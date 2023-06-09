package eu.xreco.nmr.backend.model.api.basket

import kotlinx.serialization.Serializable

/**
 * A list of [BasketPreview]s, as returned by the XRECO NMR API.
 *
 * @author Ralph Gasser
 * @version 1.0.0
 */
@Serializable
data class BasketList(val baskets: List<BasketPreview>)
