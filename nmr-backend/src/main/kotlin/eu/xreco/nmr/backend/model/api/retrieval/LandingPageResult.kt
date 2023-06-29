package eu.xreco.nmr.backend.model.api.retrieval

/**
 * A [LandingPageResult] object as returned by XRECO NRM backend retrieval API.
 *
 * @author Rahel Arnold
 * @author Ralph Gasser
 * @version 1.0.0
 */
data class LandingPageResult(val page: Int, val pageSize: Int, val count: Long, val items: List<MediaResource>)
