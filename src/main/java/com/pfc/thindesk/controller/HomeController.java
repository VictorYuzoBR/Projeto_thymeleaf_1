package com.pfc.thindesk.controller;

import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pfc.thindesk.entity.Cliente;
import com.pfc.thindesk.entity.Chamado;
import com.pfc.thindesk.entity.HorarioAtendimento;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ChamadoService chamadoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private HorarioAtendimentoService horarioAtendimentoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;



    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("layout");
        String fragment = "hello :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        modelAndView.addObject("content", fragment);
        return modelAndView;
    }

    @GetMapping("/home_client")
    public ModelAndView home_client() {
        ModelAndView modelAndView = new ModelAndView("layout_client");
        String fragment = "home :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        modelAndView.addObject("content", fragment);
        return modelAndView;
    }

    @GetMapping("/chamados")
    public String chamados(Model model) {
        List<Chamado> chamados = chamadoService.listarChamados();
        model.addAttribute("chamados", chamados); // Passa a lista para o modelo
        String fragment = "chamados :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "chamados";
    }

    @GetMapping("/clientes")
    public String clientes(Model model) {
        List<Usuario> clientes = usuarioService.listarClientes();
        model.addAttribute("clientes", clientes);
        String fragment = "clientes :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "clientes";
    }
    
    @GetMapping("/ajustes-horarios")
    public String horarios(Model model) {
        List<HorarioAtendimento> horarios = horarioAtendimentoService.listarTodos();
        model.addAttribute("ajustes-horarios", horarios);
        String fragment = "ajustes-horarios :: content";
        log.info("Carregando fragmento: {}", fragment); // Log para depuração
        model.addAttribute("content", fragment);
        return "ajustes-horarios";
    }

    @GetMapping("/cadastro-cliente")
    public String abrirCadastroCliente(Model model) {
        // Cria um objeto Cliente vazio para o formulário
        model.addAttribute("cliente", new Cliente());
        // Retorna o nome do template Thymeleaf (sem .html)
        return "cadastro-cliente";
    }

    @PostMapping("/cadastro-cliente")
    public String cadastrarCliente(@ModelAttribute Cliente cliente) {
        // salva no banco com senha criptografada e role CLIENTE
        String res = usuarioService.salvarUsuario(cliente);
        if (res == null) {
            return "redirect:/400?msg=" + URLEncoder.encode("Usuario ja existe", StandardCharsets.UTF_8);
        }
        // redireciona para login ou outra página
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginSubmit( @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               Model model) {
        try {


            if (request.getCookies() != null) {
                Cookie cookie = new Cookie("Authorization", null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }


            String token = authService.login(username, password);
            if (token == null) {
                return "login";
            } else {

                System.out.println("chegou aqui olha seu token");
                System.out.println(token);

                String role = tokenService.recoverClaim(token, "role");

                // Salva o token em cookie

                try {
                    Cookie cookie = new Cookie("Authorization", token);
                    cookie.setHttpOnly(true);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    System.out.println("teoricamente seu cookie foi salvo");

                    if (role.equals("ROLE_FUNCIONARIO")) {
                        return "redirect:/";
                    } else {
                        return "redirect:/home_client";
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    return "login";
                }


            }

        } catch (RuntimeException e) {
            // Login inválido → volta para login com mensagem de erro
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/criar-admin")
    public String criarAdmin() {
        usuarioService.criarFuncionarioPadrao();
        return "redirect:/login";
    }

    @GetMapping("/logout_screen")
    public String logout() {

        return "logout_screen";
    }





}
