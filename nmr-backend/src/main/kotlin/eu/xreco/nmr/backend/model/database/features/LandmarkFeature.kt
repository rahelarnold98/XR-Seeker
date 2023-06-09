package eu.xreco.nmr.backend.model.database.features

import eu.xreco.nmr.backend.model.database.Entity
import kotlinx.serialization.Transient
import org.vitrivr.cottontail.client.language.ddl.CreateEntity
import org.vitrivr.cottontail.client.language.ddl.CreateIndex
import org.vitrivr.cottontail.core.database.Name
import org.vitrivr.cottontail.core.types.Types
import org.vitrivr.cottontail.grpc.CottontailGrpc

/**
 * Represents a label-based landmark feature in the XRECO data model.
 *
 * @author Ralph Gasser
 * @version 1.0.0
 */
data class LandmarkFeature(
    override val mediaResourceId: String,
    override val label: String,
    override val start: Float? = null,
    override val end: Float? = null,
    override val rep: Float? = null
) : LabelFeature {

    @Transient
    override val entity: Entity = LandmarkFeature

    companion object : Entity {
        override val name: String = "features_landmark"

        override fun create(schema: String): CreateEntity = CreateEntity("$schema.$name")
            .column(name = "mediaResourceId", type = "String", nullable = false)
            .column(name = "label", type = "String", nullable = false)
            .column(name = "start", type = "Float", nullable = false)
            .column(name = "end", type = "Float", nullable = false)
            .column(name = "rep", type = "Float", nullable = false)


        override fun indexes(schema: String): List<CreateIndex> = listOf(
            CreateIndex(Name.EntityName(schema, name), CottontailGrpc.IndexType.LUCENE).name("idx_label_lucene")
                .column("label")
        )
    }
}