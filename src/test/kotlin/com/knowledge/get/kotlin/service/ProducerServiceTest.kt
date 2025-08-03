import com.knowledge.get.kotlin.model.Producer
import com.knowledge.get.kotlin.repository.ProducerRepository
import com.knowledge.get.kotlin.service.ProducerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class ProducerServiceTest {

    @Mock
    lateinit var producerRepository: ProducerRepository

    @InjectMocks
    lateinit var producerService: ProducerService

    private val sampleProducer = Producer(
        id = "p1",
        name = "Test Producer",
        country = "Ukraine",
        address = "Kyiv"
    )

    @Test
    fun `save should return saved producer`() {
        whenever(producerRepository.save(any())).thenReturn(Mono.just(sampleProducer))

        val result = producerService.save(sampleProducer)

        StepVerifier.create(result)
            .expectNext(sampleProducer)
            .verifyComplete()

        verify(producerRepository).save(sampleProducer)
    }

    @Test
    fun `getProducerById should return producer if found`() {
        whenever(producerRepository.findById("p1")).thenReturn(Mono.just(sampleProducer))

        val result = producerService.getProducerById("p1")

        StepVerifier.create(result)
            .expectNext(sampleProducer)
            .verifyComplete()

        verify(producerRepository).findById("p1")
    }

    @Test
    fun `getProducers should return paginated producers`() {
        val pageable = PageRequest.of(0, 10)
        whenever(producerRepository.findAllBy(pageable)).thenReturn(Flux.just(sampleProducer))
        whenever(producerRepository.count()).thenReturn(Mono.just(1))

        val result = producerService.getProducers(0, 10)

        StepVerifier.create(result)
            .assertNext { page ->
                assertEquals(1, page.totalElements)
                assertEquals(1, page.content.size)
                assertEquals(sampleProducer, page.content[0])
            }
            .verifyComplete()

        verify(producerRepository).findAllBy(pageable)
        verify(producerRepository).count()
    }

    @Test
    fun `updateProducer should save if found`() {
        whenever(producerRepository.findById("p1")).thenReturn(Mono.just(sampleProducer))
        whenever(producerRepository.save(sampleProducer)).thenReturn(Mono.just(sampleProducer))

        val result = producerService.updateProducer("p1", sampleProducer)

        StepVerifier.create(result)
            .expectNext(sampleProducer)
            .verifyComplete()

        verify(producerRepository).findById("p1")
        verify(producerRepository).save(sampleProducer)
    }

    @Test
    fun `updateProducer should error if not found`() {
        whenever(producerRepository.findById("p1")).thenReturn(Mono.empty())

        val result = producerService.updateProducer("p1", sampleProducer)

        StepVerifier.create(result)
            .expectErrorMatches { it is IllegalArgumentException && it.message == "Producer not found with id p1" }
            .verify()

        verify(producerRepository).findById("p1")
        verify(producerRepository, never()).save(any())
    }

    @Test
    fun `deleteById should complete`() {
        whenever(producerRepository.deleteById("p1")).thenReturn(Mono.empty())

        val result = producerService.deleteById("p1")

        StepVerifier.create(result)
            .verifyComplete()

        verify(producerRepository).deleteById("p1")
    }
}
