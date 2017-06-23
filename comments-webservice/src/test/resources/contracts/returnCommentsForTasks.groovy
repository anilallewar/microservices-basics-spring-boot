package contracts;

import org.springframework.cloud.contract.spec.Contract;

Contract.make {
	description("""
		Represents a successful scenario of getting a comments for task
		``
		given:
			comments exists for task 
		when:
			when comments requested for task
		then:
			send the comments for the task
        ```
    """)
	request {
		method 'GET'
		url '/comments/'
	}
}
