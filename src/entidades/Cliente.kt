import entidades.Pessoa
import java.math.BigDecimal

class Cliente(
    nome: String,
    val saldo: BigDecimal // Adicionando o campo BigDecimal
) : Pessoa(nome)