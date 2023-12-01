package eu.xreco.nmr.backend.api

import eu.xreco.nmr.backend.api.authentification.login
import eu.xreco.nmr.backend.api.authentification.logout
import eu.xreco.nmr.backend.api.ingest.ingest
import eu.xreco.nmr.backend.api.ingest.ingestAbort
import eu.xreco.nmr.backend.api.ingest.ingestStatus
import eu.xreco.nmr.backend.api.media.*
import eu.xreco.nmr.backend.api.retrieval.*
import eu.xreco.nmr.backend.config.Config
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.path
import kotlinx.coroutines.runBlocking
import org.vitrivr.engine.core.config.pipeline.execution.ExecutionServer
import org.vitrivr.engine.core.model.metamodel.SchemaManager
import org.vitrivr.engine.query.execution.RetrievalRuntime

/**
 * Extension function that generates the relevant routes of the XRECO NMR backend API.
 *
 * @param config The application configuration.
 * @param manager The [SchemaManager] instance
 * @param runtime The [ExecutionServer] instance.
 */
fun Javalin.initializeRoutes(config: Config, manager: SchemaManager, runtime: ExecutionServer): Javalin =  this.routes  { runBlocking {  }
    path("api") {
        path("authentication") {
            get("{username}/{password}") { login(it) }
            get("logout/{username}") { logout(it) }
        }

        /* Endpoints related to data ingest. */
        post("ingest") { ingest(it, manager, runtime) }
        path("ingest") {
            path("{jobId}") {
                get("status") { ingestStatus(it, manager, runtime) }
                delete("abort") { ingestAbort(it, manager, runtime) }
            }
        }

        /* Endpoints related to retrieval- */
        path("retrieval") {
            get("lookup/{elementId}/{entity}") { lookup(it, manager, runtime) }
            get("type/{elementId}") { type(it, manager, runtime) }
            get("text/{entity}/{text}/{pageSize}/{page}") { getFulltext(it, manager, runtime) }
            get("similarity/{entity}/{mediaResourceId}/{timestamp}/{pageSize}/{page}") {
                getSimilar(it, manager, runtime)
            }
            get("filter/{condition}/{pageSize}/{page}") { filter(it) }
        }

        /* Access to MinIO resources. */
        path("resource") {
            val minio = config.minio.newClient()
            get("{mediaResourceId}") { getAssetResource(it, minio) }
            get("{mediaResourceId}/metadata") { getMetadata(it, manager, runtime) }
        }
    }
}