package contracts.task.comments;

import org.springframework.cloud.contract.spec.Contract;

Contract.make {
	name("comments-for-task")
	description('''
Represents a successful scenario of getting a comments for task
```
given:
	comments exists for task 
when:
	when comments requested for task
then:
	send the comments for the task
```
''')
	request {
		method 'GET'
		url $(consumer(regex('/comments/([0-9a-zA-z]+)')), producer('/comments/task11'))
		headers {
			accept(applicationJson())
		}
	}
	response {
		status 200
		body( [
				[
						"taskId": "task11",
						"comment": "comment on task11",
						"posted": $(consumer('2015-04-23'),producer(execute('convertTimeValueToDate($it)')))
				],
				[
						"taskId": "task11",
						"comment":"new comment on task11",
						"posted": $(consumer("2015-04-27"),producer(execute('convertTimeValueToDate($it)')))
				]
			]
		)
		headers {
			contentType('application/json')
		}
	}
}
