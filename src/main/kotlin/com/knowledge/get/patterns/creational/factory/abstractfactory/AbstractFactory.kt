package com.knowledge.get.patterns.creational.factory.abstractfactory

//Provides a way to access functionality without caring about implementation

interface Datasource

class DatabaseDatasource: Datasource

class NetworkDatasource: Datasource

abstract class DatasourceFactory {
    abstract fun makeDatasource(): Datasource

    companion object {
        inline fun <reified T: Datasource> createFactory(): DatasourceFactory =
            when(T::class) {
                DatabaseDatasource::class -> DatabaseFactory()
                NetworkDatasource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}

class NetworkFactory: DatasourceFactory() {
    override fun makeDatasource(): Datasource = NetworkDatasource()

}

class DatabaseFactory: DatasourceFactory() {
    override fun makeDatasource(): Datasource = DatabaseDatasource()

}

fun main() {
    val datasourceFactory = DatasourceFactory.createFactory<DatabaseDatasource>()
    val datasource = datasourceFactory.makeDatasource()
    println(datasource)
}