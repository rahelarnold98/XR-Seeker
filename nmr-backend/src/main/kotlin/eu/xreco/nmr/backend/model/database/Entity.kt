package eu.xreco.nmr.backend.model.database

import org.vitrivr.cottontail.client.language.ddl.CreateEntity
import org.vitrivr.cottontail.client.language.ddl.CreateIndex

/**
 * Represents a database [Entity] (= table) in the XRECO data model.
 *
 * @author Ralph Gasser
 * @version 1.0.0
 */
interface Entity {
    /** The name of this [Entity]. */
    val name: String

    /**
     * The [CreateEntity] command for this [Entity].
     *
     * @param schema The name of the schema in which the [Entity] is located.
     * @return [CreateEntity] command that should be executed as part of the setup.
     */
    fun create(schema: String = "xreco"): CreateEntity

    /**
     * The [CreateIndex] commands that must be executed for this [Entity].
     *
     * @param schema The name of the schema in which the [Entity] is located.
     * @return A [List] of [CreateIndex] commands that should be executed as part of the setup.
     */
    fun indexes(schema: String = "xreco"): List<CreateIndex> = emptyList()
}