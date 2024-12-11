package net.rafgpereira.transpoapp.domain.model

data class Driver(
    val id: Long,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: DriverReview,
    val value: Double
)

val fakeDrivers = arrayOf(
    Driver(1,"João","Sou motorista a 10 anos","Monza", DriverReview(10, "Meh"), 20.5),
    Driver(2,"Thiago","","Kwid", DriverReview(7, "Meh"),25.0),
    Driver(3,"Paulo","Vida-loka","Toyota Corolla",DriverReview(8, "Eew"),21.2),
)