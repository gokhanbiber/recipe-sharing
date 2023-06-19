package net.azeti.challenge.recipe.configuration

import net.azeti.challenge.recipe.ingredient.Ingredient
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.function.Consumer

@Component
class JobCompletionNotificationListener (private val jdbcTemplate: JdbcTemplate) :
    JobExecutionListener {

        override fun afterJob(jobExecution: JobExecution) {
            if (jobExecution.status == BatchStatus.COMPLETED) {
                log.info("!!! JOB FINISHED! Time to verify the results")
                jdbcTemplate.query(
                    "SELECT id, unit, type, amount, calorie FROM ingredients"
                ) { rs: ResultSet, row: Int ->
                    Ingredient(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5)
                    )
                }.forEach(Consumer { ingredient: Ingredient? -> log.info("Found <{{}}> in the database.", ingredient) })
            }
        }

        companion object {
            private val log = LoggerFactory.getLogger(JobCompletionNotificationListener::class.java)
        }
}