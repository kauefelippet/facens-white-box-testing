# Teste de Caixa Branca - UI | UX e Testes de Software

Teste de caixa branca realizado por Kauê Felippe Tiburcio (RA 247721) para a disciplina de UI | UX e Testes de Software no quarto semestre de Tecnologia em Análise e Desenvolvimento de Sistemas no Centro Universitário FACENS.

## Análise de Caixa Branca Estática

| Item | Status | Artefato com Erro | Correções Necessárias |
|------|--------|-------------------|----------------------|
| O código foi devidamente documentado? | NÃO | O código não possui nenhum comentário descritivo útil sobre seu funcionamento | Adicionar JavaDoc explicando parâmetros, retornos e exceções |
| As variáveis e constantes possuem nomenclatura? | SIM |  |  |
| Existem legibilidade e organização no código? | SIM |  |  |
| Todos os NullPointers foram tratados? | NÃO | Se `conectarBD()` falhar, `conn` retorna `null`. Em `verificarUsuario()`, o código chama `conn.createStatement()` sem verificar se `conn` é `null` | Inserir validação de `conn` e tratamento de exceções |
| As conexões utilizadas foram fechadas? | NÃO | O código não fecha `Connection`, `Statement` e `ResultSet` | Usar try-with-resources para fechar `Connection`, `PreparedStatement` e `ResultSet` automaticamente. |

## Metodologia

Neste teste de caixa branca, cada método foi analisado de forma independente, respeitando o princípio de modularidade da análise de fluxo de controle. A chamada `conn = conectarBD()` presente em `verificarUsuario()` é considerada uma operação atômica sem expansão de sua lógica interna.

### Fundamentação

Conforme estabelecido por Thomas McCabe (1976) em sua definição de complexidade ciclomática, a métrica deve ser aplicada individualmente a cada módulo do software. No contexto atual, isso implica calcular a complexidade separadamente para cada método, uma vez que cada um possui seu próprio grafo de fluxo de controle e seus caminhos logicamente independentes.


---

[**McCabe, T. J. (1976)**. *A complexity measure. IEEE Transactions on Software Engineering*, SE-2(4), 308-320.](https://ieeexplore.ieee.org/document/1702388)

## Grafos de Fluxo de Controle: `conectarBD()`

![Grafo de Fluxo de Controle de conectarBD()](<Diagrama ConectarBD().png>)

### Identificação dos Nós

- **N1**: `Conn = null;`
- **N2**: Bloco Try; Catch
- **N3**: Sucesso
- **N4**: Exceção
- **N5**: `return conn`

**Total de Arestas (E)**: 5  
**Total de Nós (N)**: 5  
**Componentes Conectados (P)**: 1  

## Cálculo da Complexidade Ciclomática: `conectarBD()`

### Método 1: Fórmula E - N + 2P

```
V(G) = E - N + 2P
V(G) = 5 - 5 + 2(1)
V(G) = 5 - 5 + 2
V(G) = 2
```

### Método 2: Contagem de Regiões

Observando o grafo, temos **2 regiões**:
1. Região externa ao grafo **[R1]**
2. Região do caminho com exceção **[R2]**

**V(G) = 2 regiões** 

**Resultado**: A complexidade ciclomática de `conectarBD()` é **2**.

## Caminhos Básicos: `conectarBD()`

### Caminho 1: Sucesso na Conexão
**Sequência**: N1 → N2 → N3 → N5

### Caminho 2: Falha na Conexão
**Sequência**: N1 → N2 → N4 → N5

## Grafos de Fluxo de Controle: `verificarUsuario()`

![Grafo de Fluxo de Controle de verificarUsuario()](<Diagrama verificarUsuario().png>)

### Identificação dos Nós

- **N1**: `Connection conn = conectarBD();`
- **N2**: Construção da string SQL
- **N3**: `Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql);`
- **N4**: `if (rs.next())`
- **N5**: `result = true; nome = rs.getString("nome");`
- **N6**: Se rs.next() é falso
- **N7**: `catch (Exception e) { println(...); }`
- **N8**: `return result;`

**Total de Arestas (E)**: 9  
**Total de Nós (N)**: 8  
**Componentes Conectados (P)**: 1  

## Cálculo da Complexidade Ciclomática: `verificarUsuario()`

### Método 1: Fórmula E - N + 2P

```
V(G) = E - N + 2P
V(G) = 9 - 8 + 2(1)
V(G) = 9 - 8 + 2
V(G) = 3
```

### Método 2: Contagem de Regiões

Observando o grafo, temos **3 regiões**:
1. Região externa ao grafo **[R1]**
2. Região do caminho com exceção **[R2]**
3. Região dos caminhos de rs.next() **[R3]**

**V(G) = 3 regiões** 

**Resultado**: A complexidade ciclomática de `verificarUsuario()` é **3**.

## Caminhos Básicos: `verificarUsuario()`

### Caminho 1: Sucesso na Verificação
**Sequência**: N1 → N2 → N3 → N4 → N5 → N8

### Caminho 2: Usuário Não Encontrado
**Sequência**: N1 → N2 → N3 → N4 → N6 → N8

### Caminho 3: Exceção na Execução
**Sequência**: N1 → N2 → N3 → N7 → N8

# Código Refatorado: [User.java](refatorado/src/main/java/com/whitebox/User.java)

Para tornar o código funcional e adequado às boas práticas, foram feitas correções necessárias destacadas na análise de caixa branca estática, adicionando JavaDoc, validação de `conn`, tratamento de exceções e blocos try-with-resources.  

Para permitir a conexão com o banco de dados, foi utilizado o Maven para build com a dependência **MySQL Connector/J**:

``` xml
    <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
      <version>9.5.0</version>
    </dependency>
```

O banco de dados está localizado em um container Docker, utilizado atualmente para hospedar os dados do projeto da disciplina de StartUp One (Rodaki). Os parâmetros de inicialização do container, extraídos do [repositório remoto do projeto](https://github.com/NatanzinHPS/StartUp-One) encontram-se infracitados.

``` yml
  mysql:
    image: mysql:8.0
    container_name: Rodaki-DB
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQL_DATABASE}
      MYSQL_USER: ${MYSQL_USER}
      MYSQL_PASSWORD: ${MYSQL_PASSWORD}
    ports:
      - "3306:3306"
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-uroot", "-p${MYSQL_ROOT_PASSWORD}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 30s
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - Rodaki
```  

Em `verificarUsuario()`, os parâmetros da consulta foram alterados para adequar-se às colunas da tabela `users`, e o retorno ajustado para comprovar sucesso exibindo o e-mail do usuário no terminal:

``` java
            String sql = "SELECT email FROM users WHERE name = ? AND password_hash = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, login);
                pst.setString(2, senha);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        result = true;
                        email = rs.getString("email");
                        System.out.println("E-mail localizado: " + email);
                    } else {
                        result = false;
                        email = "";
                    }
                }
            }
```

## Execução do código refatorado

O código mostra-se funcional ao executar a classe [App.java](refatorado/src/main/java/com/whitebox/App.java), estando abaixo anexados o método main, output no terminal e tabela do banco de dados, respectivamente.

``` java
public class App 
{
    public static void main( String[] args )
    {
        User user = new User();

        System.out.println(user.conectarBD());
        boolean sucesso = user.verificarUsuario("Paulo", "$2a$10$.7UwBVoG8BX6.Wu7hidK6uZMoeBGQIMwH7yquHyM/6W8g3s6AqFsS");

        System.out.println(sucesso);
    }
}
```

![Output no terminal](<Output Refatorado.png>)

![Tabela users](<Tabela users.png>)