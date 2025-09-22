# Aplicação de cadastro e login básica Thymeleaf + SpringBoot + Mongo Atlas

## DESCRIÇÃO
Projeto realizado para aprendizado de Thymeleaf e MongoDB

## Dependências utilizadas
* Spring web: para criação de projetos web.
* Lombok: para diminuição de códigos padrões e uma codificação mais limpa.
* Spring security: para configurações de segurança da aplicação.
* Java JWT: para criação de access tokens e refresh tokens.
* MongoDB starter: para conexão com mongoDB
* Thymeleaf Starter e Thymeleaf layout dialect: para criação de templates em thymeleaf.

```java

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.pfc</groupId>
    <artifactId>thindesk</artifactId>
    <version>1.0-alpha</version>
    <packaging>war</packaging>
    <name>thindesk</name>

    <properties>
        <endorsed.dir>${project.build.directory}/endorsed</endorsed.dir>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>javax</groupId>
            <artifactId>javaee-web-api</artifactId>
            <version>8.0.1</version>
            <scope>provided</scope>
        </dependency>
        <!-- Spring Boot Web para a API REST -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.4.0</version>
        </dependency>
        <!-- Spring token jwt -->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>4.5.0</version>
        </dependency>
        <!-- Spring Boot MongoDB Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
            <version>3.4.0</version>
        </dependency>
        <!-- Thymeleaf -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
            <version>3.4.0</version>
        </dependency>
        <dependency>
            <groupId>nz.net.ultraq.thymeleaf</groupId>
            <artifactId>thymeleaf-layout-dialect</artifactId>
            <version>3.3.0</version>
        </dependency>    
        <!-- Spring Boot Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <version>3.4.0</version>
        </dependency>
        <!-- Lombok para facilitar o desenvolvimento -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.36</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>3.4.0</version>
                <configuration>
                    <mainClass>com.pfc.ThindeskApplication</mainClass>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <compilerArgs>
                        <arg>-Xlint:deprecation</arg>
                    </compilerArgs>
                    <release>23</release>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.4.0</version>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>3.8.1</version>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>copy</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${endorsed.dir}</outputDirectory>
                            <silent>true</silent>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>javax</groupId>
                                    <artifactId>javaee-endorsed-api</artifactId>
                                    <version>7.0</version>
                                    <type>jar</type>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

## Aplicações utilizadas
* IntelliJ para construção do projeto
* Postman para testes de requisições
* MongoDB atlas online para gerenciamento do banco de dados

## Objetivos a serem atingidos

* Implementar cadastro, login e logout de usuários com segurança adequada (hash de senhas, validação de dados).
* Controlar o acesso a rotas e páginas com base no perfil do usuário (roles).
* Utilizar Spring Security para gerenciamento de autenticação e autorização.
* Utilizar MongoDB Atlas como banco de dados para armazenamento das informações de usuários e sessões.
* Construir a interface utilizando Thymeleaf, mantendo o design desacoplado da lógica do negócio para facilitar futuras customizações temáticas.

## Explicações dos objetivos

### Implementar cadastro, login e logout de usuários com segurança adequada (hash de senhas, validação de dados).

Ao acessar a rota /login, o usuário irá se deparar com a seguinte tela:

<img width="563" height="687" alt="image" src="https://github.com/user-attachments/assets/52e2a8b9-0889-42f5-8a03-34aa30af712a" />

No momento, ele pode fazer 3 ações principais: fornecer as credenciais e realizar o login, ir para tela de criação de usuário e clicar no botão de gerar funcionário.

O botão de gerar funcionário irá criar automaticamente um usuário no banco de dados com usuário admin e senha admin123

<img width="1310" height="172" alt="image" src="https://github.com/user-attachments/assets/bfb99c67-dcfd-4fe7-9bd6-91d5569538d7" />

Este botão está presente apenas para fins de testes, onde o login com função de funcionário e cliente terão acessos a páginas diferentes.

Ao clicar para criar uma nova conta, o usuário é redirecionado para página de cadastro com verificações básicas nos campos de usuário e senha.

<img width="487" height="549" alt="image" src="https://github.com/user-attachments/assets/566e50e4-1596-42f5-af8c-9d51e07b10e9" />

<img width="503" height="552" alt="image" src="https://github.com/user-attachments/assets/c577d429-39cb-46cd-990d-ac3ad467f29f" />

<img width="566" height="566" alt="image" src="https://github.com/user-attachments/assets/ca5f5662-7761-484e-8e4b-77058460d321" />

<img width="565" height="185" alt="image" src="https://github.com/user-attachments/assets/0ff49c6d-b29f-420a-bc78-1a8b8248e494" />

As verificações são feitas utilizando Regex (expressões regulares) no atributo pattern dos campos: 

```java
    <label>Usuário:</label>
    <p style="color: red; font-weight: bold;">Atenção! O usuário será salvo apenas com letras minúsculas.</p>
    <input type="text"
           th:field="*{usuario}"
           required
           pattern=".{8,}"
           title="O usuario deve ter no minimo 8 letras" />

    <label>Senha:</label>
    <input type="password"
           th:field="*{senha}"
           required
           pattern="(?=.*[A-Z]).{8,}"
           title="A senha deve ter no mínimo 8 caracteres e conter pelo menos uma letra maiúscula" />
```

A senha é codificada no banco de dados com a utilização do codificador do Bcrypt, que vem embutido na dependência principal do Spring Security.


Criação do bean que será utilizado
```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```

### Controlar o acesso a rotas e páginas com base no perfil do usuário (roles).

Após a criação de um usuário ou utilização do usuário criado com o botão, o usuário poderá realizar o acesso ao sistema.

Ao acessar com a conta de funcionário, ele será redirecionado para a seguinte pagina home:

<img width="1401" height="859" alt="image" src="https://github.com/user-attachments/assets/3bb626a4-5a3a-4a38-ac9d-b2324b3b558f" />

No momento, a maioria dos links da barra lateral apenas enviam para uma página de em construção:

<img width="747" height="468" alt="image" src="https://github.com/user-attachments/assets/7bb6e696-230d-4585-a4d6-af4a78e6db30" />

E como última opção, a opção para sair do sistema:

<img width="1773" height="543" alt="image" src="https://github.com/user-attachments/assets/b9e761c2-b423-427b-83cc-e707abcf180c" />

Caso seja realizado o acesso utilizando a conta de cliente, o usuário será redirecionado para home do cliente:

<img width="1520" height="890" alt="image" src="https://github.com/user-attachments/assets/dbf78f16-17cc-4b35-b48e-00f9b043ef33" />

Que no momento possui apenas a opção de logout.

Em caso de tentativa de acesso as rotas por meio da url, a seguinte página será apresentada:

<img width="431" height="275" alt="image" src="https://github.com/user-attachments/assets/f2e7809c-ad3e-4dad-9389-3ceba94c996c" />

Tanto para tentativas de acesso sem login, quanto para um usuário tentando acessar rotas de outra classe.

A verificação é feita por meio de token JWT + autoridades:

```java
    public String gerarToken(Usuario usuario) {
        return JWT.create()
                .withIssuer("yuzo") // eu
                .withSubject(usuario.getUsername())       // nome do usuário
                .withClaim("role", usuario.getRole().name()) // role no token
                .withIssuedAt(new Date())                 // data de emissão
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // expiração
                .sign(Algorithm.HMAC256(SECRET));        // assinatura HMAC
    }
```

Durante a criação do token, a sua role é salva no claim que será utilizado para redirecionamento das homes, e seu username é salvo no subject, que será utilizado para
verificar as permissões que sua classe possui.

Em cada requisição, o token passa por um filtro de segurança que irá realizar a sua validação, e salvar no contexto de segurança as suas permissões, logo em seguida, 
o filtro retorna para o arquivo de segurança de configuração, onde estão definidas quais classes podem acessar quais rotas: 

```java
  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF desabilitado
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/cadastro-cliente","/login", "/css/**", "/js/**", "/images/**", "/favicon.ico","/logout","/error").permitAll()
                        // Somente FUNCIONARIO pode acessar /chamados e subrotas
                        .requestMatchers("/chamados/**").hasRole("FUNCIONARIO")
                        .requestMatchers("/clientes/**").hasRole("FUNCIONARIO")
                        .requestMatchers("/").hasRole("FUNCIONARIO")
                        // Somente CLIENTE pode acessar /home_client e subrotas
                        .requestMatchers("/home_client/**").hasRole("CLIENTE")
                        .anyRequest().permitAll() // todas as outras rotas precisam de autenticação
                ).logout(logout -> logout
                        .logoutUrl("/logout") // ou outra URL que você quiser para logout
                        .deleteCookies("Authorization") // nome do cookie que você quer apagar
                        .logoutSuccessUrl("/login") // redireciona após logout
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // sem sessão
                );

        // Adiciona o filtro customizado antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
```
O token fica armazenado por meio dos cookies do navegador com nome Authorization, e pode ser encontrado nas ferramentas de desenvolvedor:

<img width="512" height="658" alt="image" src="https://github.com/user-attachments/assets/e210a583-55ab-47a2-8f43-ff157edff7a0" />

### Construir a interface utilizando Thymeleaf, mantendo o design desacoplado da lógica do negócio para facilitar futuras customizações temáticas.

As interfaces foram criadas com a utilização do thymeleaf e podem ser encontradas na pasta resources: 

<img width="241" height="525" alt="image" src="https://github.com/user-attachments/assets/fa46b431-611f-4bb9-8ef1-03dbbae283bf" />

## Passo a passo para rodar o projeto em sua máquina:

* Realize o download do zip ou clone o repositório.
* Com o projeto aberto em sua máquina, vá para o arquivo application.properties e encontre o seguinte código:

```java
# Configurando a conexao com MongoDB  
spring.data.mongodb.uri=seu url aqui
spring.data.mongodb.database=seu cluster aqui
spring.data.mongodb.auto-index-creation=true
spring.mongodb.embedded.data=file:src/main/resources/chamados.json

spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.cache=false

logging.level.com.atrah=DEBUG
```
Onde está escrito seu url aqui, você precisa colocar a sua String de conexão do seu cluster MongoDB, caso você ainda não tenha um, entre no site do
MongoDB atlas, e siga o passo a passo para criação de um cluster gratuíto, após a sua criação, você poderá clicar na opção de conectar e gerar sua string
de conexão: 

<img width="1115" height="347" alt="image" src="https://github.com/user-attachments/assets/377e212a-b02b-4edb-82fe-fbf77fab1e23" />

Onde está escrito seu cluster aqui, apenas coloque o nome do seu cluster, no meu exemplo seria teste.

Após isso, o projeto estará pronto para rodar.
Você poderá observar a criação das collections do projeto caso clique em Browse collections e clique no nome do seu cluster.

<img width="1684" height="422" alt="image" src="https://github.com/user-attachments/assets/40f1c3fc-8986-434c-994a-7c75ce9b999a" />




  










