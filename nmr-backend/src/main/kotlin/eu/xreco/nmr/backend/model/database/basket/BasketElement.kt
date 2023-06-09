package eu.xreco.nmr.backend.model.database.basket

import eu.xreco.nmr.backend.model.database.Entity
import eu.xreco.nmr.backend.model.database.EntityObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.vitrivr.cottontail.client.language.ddl.CreateEntity
import org.vitrivr.cottontail.core.database.Name
import org.vitrivr.cottontail.core.types.Types

/**
 * A [BasketElement] in the XRECO data model.
 *
 * @author Ralph Gasser
 * @version 1.0.0
 */
data class BasketElement(val basketId: Int, val mediaResourceId: String): EntityObject {

    @Transient
    override val entity: Entity = BasketElement

    companion object: Entity {
        override val name: String = "basket_elements"

        override fun create(schema: String): CreateEntity = CreateEntity("$schema.$name")
            .column(name = "basketId", type = "Int", nullable = false)
            .column(name = "mediaResourceId", type = "String", nullable = false)

    }
}