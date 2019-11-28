package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

Contract.make {
    description("this should return all Backlog")

    request {
        method('GET')
        url('backlog')
    }

    response {
        status(HttpStatus.OK.value())
        headers {
            contentType(
                    MediaType.APPLICATION_JSON_VALUE
            )
        }
        body([[inviteName: "Oleg"]])
    }
}