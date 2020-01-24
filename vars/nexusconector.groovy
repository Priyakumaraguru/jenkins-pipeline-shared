def call()
{
sh 'curl -X POST -i -H  "Accept: application/json" -H "Content-Type: application/json" -trace-ascii -d "@https://github.com/Priyakumaraguru/nexus/blob/master/repo.json" -u admin:admin123 "http://3.15.18.214:8081/nexus/service/local/repositories"'
}
