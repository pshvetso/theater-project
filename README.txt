
TODO

- clean code, save schema, git, update cvs(3)
- fix idea code analysis messages
- schedule
- docker deployment with production profile
- users, security modules
- crud,search,filter-react - see screenshots
	https://www.youtube.com/watch?v=rDSnpb23bb4
	http://data-creative.info/projects/2016/07/10/crud-react-app/
- sliding left-side panel with cool gradient
- dashboard, admin panel
- global search with suggestions and history
- calendar - see outlook
- readme: technologies, architecture, data schema

To run the app use:
docker run -d -p 8081:8080 --name theater springio/theater

Schema relations:
-- movie <- sessions(typed in) -> hall -> theater

