## Working with intellij

To work with project form intellij after sbt import (and pretty much any change to Project structure) you need to:

1. Open: `File -> Project Structure` navigate to `Modules` and select `intellijPlugin` and set SDK to Intellij one (most likely you would need to create one)
2. Navigate to `.idea/modules/intellij-toolkit.iml` and change type parameters to `type="PLUGIN_MODULE"` in `module` tag  and add 
  
   ```xml
   <component name="DevKit.ModuleBuildProperties" url="file://<path-of-ws>/src/main/resources/plugin.xml" />
   ``` 
   
3. Rebuilt whole project and enjoy   
   
