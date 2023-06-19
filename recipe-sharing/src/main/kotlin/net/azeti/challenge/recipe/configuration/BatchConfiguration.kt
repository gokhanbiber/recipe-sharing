package net.azeti.challenge.recipe.configuration

import net.azeti.challenge.recipe.ingredient.Ingredient
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class BatchConfiguration {
    @Bean
    fun reader(): FlatFileItemReader<Ingredient> {
        return FlatFileItemReaderBuilder<Ingredient>()
            .name("itemReader")
            .resource(ClassPathResource("ingredients.csv"))
            .delimited()
            .names(*arrayOf("id", "unit", "type", "amount", "calorie"))
            .fieldSetMapper(object : BeanWrapperFieldSetMapper<Ingredient?>() {
                init {
                    setTargetType(Ingredient::class.java)
                }
            })
            .linesToSkip(1)
            .build();
    }

    @Bean
    fun writer(dataSource: DataSource?): JdbcBatchItemWriter<Ingredient> {
        return JdbcBatchItemWriterBuilder<Ingredient>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql("INSERT INTO ingredients (id, unit, type, amount, calorie) VALUES (:id, :unit, :type, :amount, :calorie)")
            .dataSource(dataSource!!)
            .build()
    }

    @Bean
    fun importUserJob(
        jobRepository: JobRepository?,
        listener: JobCompletionNotificationListener?, step1: Step?
    ): Job {
        return JobBuilder("importUserJob", jobRepository!!)
            .incrementer(RunIdIncrementer())
            .listener(listener!!)
            .flow(step1!!)
            .end()
            .build()
    }

    @Bean
    fun step1(
        jobRepository: JobRepository?,
        transactionManager: PlatformTransactionManager?, writer: JdbcBatchItemWriter<Ingredient>?
    ): Step {
        return StepBuilder("step1", jobRepository!!)
            .chunk<Ingredient, Ingredient>(10, transactionManager!!)
            .reader(reader())
            .writer(writer!!)
            .build()
    }
}