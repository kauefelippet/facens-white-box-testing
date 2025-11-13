# Teste de Caixa Branca - UI | UX e Testes de Software

Teste de caixa branca realizado por Kauê Felippe Tiburcio (RA 247721) para a disciplina de UI | UX e Testes de Software no quarto semestre de Tecnologia em Análise e Desenvolvimento de Sistemas no Centro Universitário FACENS.

## Análise de Caixa Branca Estática

| Item | Status | Artefato com Erro | Correções Necessárias |
|------|--------|-------------------|----------------------|
| O código foi devidamente documentado? | NÃO | O código não possui nenhum comentário descritivo sobre seu funcionamento | Adicionar JavaDoc explicando parâmetros, retornos e exceções |
| As variáveis e constantes possuem nomenclatura? | SIM |  |  |
| Existem legibilidade e organização no código? | SIM |  |  |
| Todos os NullPointers foram tratados? | NÃO | Se `conectarBD()` falhar, `conn` retorna `null`. Em `verificarUsuario()`, o código chama `conn.createStatement()` sem verificar se `conn` é `null` | Inserir validação de `conn` e tratamento de exceções |
| As conexões utilizadas foram fechadas? | NÃO | O código não fecha `Connection`, `Statement` e `ResultSet` | Inserir `close()` |

## Metodologia

No presente teste de caixa branca, cada método foi analisado independentemente. A chamada conn = conectarBD() dentro de verificarUsuario() é tratada como uma operação atômica (uma única instrução), não expandindo o que acontece internamente.

### Fundamentação

Segundo Thomas McCabe (1976), a complexidade ciclomática deve ser aplicada a cada módulo individualmente e, no presente contexto, isso significa calcular separadamente para cada método do sistema.

---

[**McCabe, T. J. (1976)**. *A complexity measure. IEEE Transactions on Software Engineering*, SE-2(4), 308-320.](https://ieeexplore.ieee.org/document/1702388)

### Grafos de Fluxo de Controle

#### conectarBD()

![Grafo de Fluxo de Controle de conectarBD()](<Diagrama ConectarBD().png>)

##### Identificação dos Nós

- **N1**: `Conn = null;`
- **N2**: Bloco Try; Catch
- **N3**: Sucesso
- **N4**: Exceção
- **N5**: `return conn`

**Total de Arestas (E)**: 5  
**Total de Nós (N)**: 5  
**Componentes Conectados (P)**: 1  

#### verificarUsuario()

![Grafo de Fluxo de Controle de verificarUsuario()](<Diagrama verificarUsuario().png>)

##### Identificação dos Nós

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

## Cálculo da Complexidade Ciclomática: conectarBD()

### Método 1: Fórmula E - N + 2P

```
V(G) = E - N + 2P
V(G) = 5 - 5 + 2(1)
V(G) = 5 - 5 + 2
V(G) = 2
```

### Método 2: Contagem de Regiões

Observando o grafo, temos **2 regiões**:
1. Região externa ao grafo
2. Região do caminho com exceção

**V(G) = 2 regiões** 

**Resultado**: A complexidade ciclomática de conectarBD() é **2**.

## Cálculo da Complexidade Ciclomática: verificarUsuario()

### Método 1: Fórmula E - N + 2P

```
V(G) = E - N + 2P
V(G) = 9 - 8 + 2(1)
V(G) = 9 - 8 + 2
V(G) = 3
```

### Método 2: Contagem de Regiões

Observando o grafo, temos **3 regiões**:
1. Região externa ao grafo
2. Região do caminho com exceção
3. Região dos caminhos de rs.next()

**V(G) = 3 regiões** 

**Resultado**: A complexidade ciclomática de verificarUsuario() é **3**.

## Caminhos Básicos: verificarUsuario()

### Caminho 1: Sucesso na Verificação
**Sequência**: N1 → N2 → N3 → N4 → N5 → N8

### Caminho 2: Usuário Não Encontrado
**Sequência**: N1 → N2 → N3 → N4 → N6 → N8

### Caminho 3: Exceção na Execução
**Sequência**: N1 → N2 → N3 → N7 → N8

## Caminhos Básicos: conectarBD()

### Caminho 1: Sucesso na Conexão
**Sequência**: N1 → N2 → N3 → N5

### Caminho 2: Falha na Conexão
**Sequência**: N1 → N2 → N4 → N5
