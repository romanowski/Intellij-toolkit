## Working with intellij

To work with project form intellij after sbt import (and pretty much any change to Project structure) you need to:

5. Open: `File -> Project Structure` navigate to `Modules` and select `intellijPlugin` and set SDK to Intellij one (most likely you would need to create one)
4. Navigate to `.idea/modules/intellij-toolkit.iml` and change type parameters to `type="PLUGIN_MODULE"` in `module` tag  and add 
  
   ```xml
   <component name="DevKit.ModuleBuildProperties" url="file://<path-of-ws>/src/main/resources/plugin.xml" />
   ``` 
   
5. Rebuilt whole project and enjoy   
   