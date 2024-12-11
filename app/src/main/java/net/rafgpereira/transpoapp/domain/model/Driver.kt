package net.rafgpereira.transpoapp.domain.model

data class Driver(
    val id: Long,
    val name: String,
    val description: String?,
    val vehicle: String?,
    val review: DriverReview?,
    val value: Double?
)

val fakeDrivers = listOf(
    Driver(1,
        "Homer Simpson",
        "Olá! Sou o Homer, seu motorista camarada! Relaxe e aproveite o passeio, com direito a rosquinhas e boas risadas (e talvez alguns desvios).",
        "Plymouth Valiant 1973 rosa e enferrujado",
        DriverReview(
            10,
            "Motorista simpático, mas errou o caminho 3 vezes. O carro cheira a donuts."),
        50.05),
    Driver(2,
        "Dominic Toretto",
        "Ei, aqui é o Dom. Pode entrar, vou te levar com segurança e rapidez ao seu destino. Só não mexa no rádio, a playlist é sagrada.",
        "Dodge Charger R/T 1970 modificado",
        DriverReview(
            4,
            "Que viagem incrível! O carro é um show à parte e o motorista, apesar de ter uma cara de poucos amigos, foi super gente boa. Recomendo!"),
        100.09),
    Driver(3,
        "James Bond",
        "Boa noite, sou James Bond. À seu dispor para um passeio suave e discreto. Aperte o cinto e aproveite a viagem.",
        "Aston Martin DB5 clássico",
        DriverReview(
            5,
            "Serviço impecável! O motorista é a própria definição de classe e o carro é simplesmente magnífico. Uma experiência digna de um agente secreto."),
        200.18),
)