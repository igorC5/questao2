import entidades.Pessoa
import org.example.crud.EntidadeJDBC

// IGOR CANDIDO RA:60003932
// JHAYMES 60001848
// EDUARDO BELISKI 60000775
// BRUNO VENZO 60002801

val conectar = EntidadeJDBC(
    url = "jdbc:postgresql://localhost:5432/teste1",
    usuario = "postgres",
    senha = "postgres"
)

fun criarTabela() {
    val sql = "CREATE TABLE IF NOT EXISTS Pessoa" +
            " (" +
            " nome varchar(255)," +
            ")"

    val banco = conectar.conectarComBanco()
    val enviarParaBanco = banco!!.createStatement().execute(sql)

    println(enviarParaBanco)

    banco.close()
}

fun cadastrarCliente() {
    val t = Pessoa(
        nome = "IGOR"
    )

    val banco = conectar.conectarComBanco()!!
    val sql = banco.prepareStatement(
        "INSERT INTO CaixaDAgua" +
                " (material, capacidade, altura, " +
                " largura, profundidade, blablablabla, preco)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)"
    )
    sql.setString(1, t.nome)
    sql.executeUpdate()
    sql.close()
}

fun main() {
    criarTabela()
    cadastrarCliente()
}

// fizemos seguindo o código das aulas