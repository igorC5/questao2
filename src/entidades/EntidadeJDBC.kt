import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class EntidadeJDBC(
    private val url: String,
    private val usuario: String,
    private val senha: String
) {
    private fun conectarComBanco(): Connection? {
        return try {
            DriverManager.getConnection(url, usuario, senha)
        } catch (e: SQLException) {
            println("ERRO: Falha na conexão com o banco de dados.")
            e.printStackTrace()
            null
        }
    }

    fun criarTabelaCliente() {
        val sql = """
            CREATE TABLE IF NOT EXISTS Cliente (
                id SERIAL PRIMARY KEY,
                nome VARCHAR(255) NOT NULL,
                saldo NUMERIC(15, 2) NOT NULL -- Tipo ideal para BigDecimal (15 dígitos totais, 2 decimais)
            )
        """.trimIndent()

        try {
            conectarComBanco().use { conn ->
                conn?.createStatement()?.execute(sql)
                println("Tabela 'Cliente' verificada/criada com sucesso.")
            }
        } catch (e: SQLException) {
            println("ERRO ao criar a tabela 'Cliente'.")
            e.printStackTrace()
        }
    }

    fun cadastrarCliente(cliente: Cliente) {
        val sql = "INSERT INTO Cliente (nome, saldo) VALUES (?, ?)"

        try {
            conectarComBanco().use { conn ->
                val pstmt = conn?.prepareStatement(sql)
                pstmt?.setString(1, cliente.nome)
                pstmt?.setBigDecimal(2, cliente.saldo) // Método correto para BigDecimal

                val resultado = pstmt?.executeUpdate()
                if (resultado != null && resultado > 0) {
                    println("Cliente '${cliente.nome}' cadastrado com sucesso!")
                }
                pstmt?.close()
            }
        } catch (e: SQLException) {
            println("ERRO ao cadastrar cliente.")
            e.printStackTrace()
        }
    }

    fun resgatarClientePorNome(nomePesquisa: String): Cliente? {
        val sql = "SELECT nome, saldo FROM Cliente WHERE nome = ?"
        var cliente: Cliente? = null

        try {
            conectarComBanco().use { conn ->
                val pstmt = conn?.prepareStatement(sql)
                pstmt?.setString(1, nomePesquisa)

                val rs = pstmt?.executeQuery()

                if (rs?.next() == true) {
                    val nome = rs.getString("nome")
                    val saldo = rs.getBigDecimal("saldo") // Método correto para resgatar BigDecimal

                    cliente = Cliente(nome = nome, saldo = saldo)
                    println("Cliente '${nome}' resgatado do banco de dados.")
                } else {
                    println("Nenhum cliente encontrado com o nome '$nomePesquisa'.")
                }
                rs?.close()
                pstmt?.close()
            }
        } catch (e: SQLException) {
            println("ERRO ao resgatar cliente.")
            e.printStackTrace()
        }
        return cliente
    }
}