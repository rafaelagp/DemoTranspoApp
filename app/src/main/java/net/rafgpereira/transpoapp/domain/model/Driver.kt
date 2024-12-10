package net.rafgpereira.transpoapp.domain.model

//TODO replace temporary driver class
data class TempDriver(
    val name: String,
    val desc: String,
    val vehicle: String,
    val rating: String,
    val cost: String,
)

val fakeDrivers = arrayOf(
    TempDriver("João","Sou motorista a 10 anos","Monza","8","$20"),
    TempDriver("Thiago","","Kwid","10","$25"),
    TempDriver("Paulo","Vida-loka","Toyota Corolla","5","$21"),
)