package eu.xreco.nmr.backend.features.certh

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.vitrivr.engine.base.features.averagecolor.AverageColorRetriever
import org.vitrivr.engine.core.context.QueryContext
import org.vitrivr.engine.core.features.AbstractRetriever
import org.vitrivr.engine.core.model.content.element.ContentElement
import org.vitrivr.engine.core.model.content.element.Model3DContent
import org.vitrivr.engine.core.model.descriptor.vector.FloatVectorDescriptor
import org.vitrivr.engine.core.model.metamodel.Schema
import org.vitrivr.engine.core.model.query.proximity.Distance
import org.vitrivr.engine.core.model.query.proximity.ProximityQuery
import org.vitrivr.engine.core.model.retrievable.Retrieved
import org.vitrivr.engine.core.model.retrievable.attributes.DistanceAttribute
import org.vitrivr.engine.core.model.retrievable.attributes.ScoreAttribute

/**
 * [CERTHRetriever] implementation for external CERTH 3d model feature retrieval.
 *
 * @param field Schema field for which the retriever operates.
 * @param query The query vector for proximity-based retrieval.
 * @param context The [QueryContext] used to execute the query with.
 *
 * @see [AbstractRetriever]
 * @see [ProximityQuery]
 *
 * @author Rahel Arnold
 * @version 1.0.0
 */
class CERTHRetriever(
    field: Schema.Field<Model3DContent, FloatVectorDescriptor>,
    query: FloatVectorDescriptor,
    context: QueryContext
) : AbstractRetriever<Model3DContent, FloatVectorDescriptor>(field, query, context) {

    companion object {
        fun scoringFunction(retrieved: Retrieved): Float {
            val distance = retrieved.filteredAttribute<DistanceAttribute>()?.distance ?: return 0f
            return 1f - distance
        }
    }

    override fun toFlow(scope: CoroutineScope): Flow<Retrieved> {

        val k = context.getProperty(field.fieldName, "limit")?.toIntOrNull() ?: 1000 //TODO get limit
        val returnDescriptor =
            context.getProperty(field.fieldName, "returnDescriptor")?.toBooleanStrictOrNull() ?: false


        val reader = field.getReader()

        val query = ProximityQuery(
            descriptor = this@CERTHRetriever.query,
            k = k,
            distance = Distance.COSINE,
            withDescriptor = returnDescriptor
        )

        return flow {
            reader.getAll(query).forEach {
                it.addAttribute(ScoreAttribute(scoringFunction(it)))
                emit(
                    it
                )
            }
        }
    }
}
