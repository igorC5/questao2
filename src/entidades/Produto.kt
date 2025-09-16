package entidades

import java.math.BigDecimal

// Classe base que agora inclui um ID e um preço.
open class Produto(
    val id: Int = 0, // É uma boa prática ter um ID para identificar registros
    val nome: String,
    val preco: BigDecimal
)