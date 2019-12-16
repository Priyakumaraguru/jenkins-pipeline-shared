import jenkins.model.*
 jenkins = Jenkins.instance
 
def  call(String name ="name",int s=0,int e=0)
{
 
 Jenkins.instance.getItemByFullName("${name}").builds.findAll { it.number > s && it.number < e }.each { it.delete() }
}
