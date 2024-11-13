package kis.kan.jetreadearapp.model

data class Offer(
    val finskyOfferType: Int,
    val listPrice: ListPriceX,
    val rentalDuration: RentalDuration,
    val retailPrice: RetailPrice
)