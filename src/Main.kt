import java.math.BigDecimal

// IGOR CANDIDO RA:60003932
// JHAYMES 60001848
// EDUARDO BELISKI 60000775
// BRUNO VENZO 60002801
// CLEITON JUSTO 60000434

fun main() {
    val jdbc = EntidadeJDBC(
        url = "jdbc:postgresql://localhost:5432/teste1",
        usuario = "postgres",
        senha = "postgres"
    )

    // 1. Garante que a estrutura do banco de dados está pronta
    jdbc.criarTabelaCliente()

    println("\n--- Passo 1: Cadastrando um novo cliente ---")
    // 2. Cria um objeto Cliente com um valor BigDecimal
    //    Usar String no construtor do BigDecimal é a melhor prática para evitar imprecisão
    val clienteParaCadastrar = Cliente(
        nome = "Jhaymes",
        saldo = BigDecimal("12345.67")
    )

    // 3. Salva o objeto no banco de dados
    jdbc.cadastrarCliente(clienteParaCadastrar)

    println("\n--- Passo 2: Resgatando a informação do banco ---")
    // 4. Busca no banco pelo cliente que acabamos de inserir
    val clienteResgatado = jdbc.resgatarClientePorNome("Jhaymes")

    println("\n--- Passo 3: Verificando e utilizando o objeto resgatado ---")
    // 5. Verifica se o cliente foi encontrado e exibe seus dados
    if (clienteResgatado != null) {
        println("Dados do Objeto Cliente criado a partir do banco:")
        println("Nome: ${clienteResgatado.nome}")
        println("Saldo: R$ ${clienteResgatado.saldo}")
        println("A classe do atributo 'saldo' é: ${clienteResgatado.saldo::class.simpleName}")

        // Exemplo de operação matemática com o BigDecimal resgatado
        val juros = clienteResgatado.saldo.multiply(BigDecimal("0.05")) // Calcula 5% de juros
        val saldoComJuros = clienteResgatado.saldo.add(juros)

        println("\nCálculo de exemplo com o saldo resgatado:")
        println("Saldo com 5% de juros: R$ ${"%.2f".format(saldoComJuros)}")
    } else {
        println("Não foi possível realizar as operações pois o cliente não foi encontrado.")
    }
}