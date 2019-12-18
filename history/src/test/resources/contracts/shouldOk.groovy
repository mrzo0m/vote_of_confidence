package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/backlog'
    }
    response {
        status OK()
    }
}