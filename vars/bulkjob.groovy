import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
	def call(body){
long startTime = System.currentTimeMillis();

//Get value from String Parameter
MAX_BUILDS = manager.build.buildVariables.get("MAX_BUILDS").toInteger()

for (job in Jenkins.instance.items)  
            if(folder!=null && folder.exists()) //Check if the Workspace folder exists
            {
                // Get all files and folders within the Workspace of current job. 
                // Iterate through only folders and sort em by Modified Date.
                
              	sprint_number = null
              	
              	//Determine which sprint_number to delete
                File[] files = new File(workspace).listFiles().each{
                     
                  if(it!=null && it.exists() && !it.isFile()){
				  
                    sprint_number = it.name.find( /\d+/ )!=null ? it.name.find( /\d+/ ).toInteger() : null  
  
                    if(sprint_number!=null)
                    {       
                     
                          List<String> list = new ArrayList<String>()
                          list.add(it.absolutePath);
                          sprint_paths_map.put(sprint_number, list)
          
                    }
                    sprint_number = null
                  }
                }
              	
              	int count = 0;
              
                //Delete the folders based on the build number
				
              	for(Map.Entry<Integer, List<String>> entry : sprint_paths_map.entrySet())
              	{
              		if(count >= MAX_BUILDS) {
                  		deleteFolders(entry.getValue())
                    }
                  	
                    else {
                      	manager.listener.logger.println "Save - " + entry
                    }
                    
                  count++
                }
              	
            }
            else
            {
                manager.listener.logger.println "Workspace is empty or doesn't exist"
            }
        }
        else
        {
            manager.listener.logger.println "No Workspace is associated with this job"
        }
    
}

//Function to delete the folder

void deleteFolders(List<String> paths){
  
  manager.listener.logger.println "To Delete"
  
  for(String path : paths)
  {
    File file = new File(path)
    
    if( ! file.isFile() && file.exists())
    {
        file.deleteDir()
        manager.listener.logger.println "Deleted -" + path
    }
  }
}

//For Descending order TreeMap

class DescOrder implements Comparator<Integer> {
	@Override
	public int compare(Integer o1, Integer o2) {
	    return o2.compareTo(o1);
	}
}

long endTime   = System.currentTimeMillis();
long totalTime = (endTime - startTime)/1000;

manager.listener.logger.println "Total Run time in seconds : " + totalTime
}
