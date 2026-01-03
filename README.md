# Dummy Analog Device Plugin

Version 2.02.18 of PAMGuard includes a more flexible plugin loader which can more easily be used with multiple different plugin types. This 
has been implemented in the [AnalogDevicesManager](https://github.com/PAMGuard/PAMGuard/blob/main/src/analoginput/AnalogDevicesManager.java) 
to make it easy to incorporate new, and possible very platform specific, analog sensor inputs. This project can be used as an example of
how to make your own [AnalogDevicePlugin](https://github.com/PAMGuard/PAMGuard/blob/main/src/analoginput/AnalogDevicePlugin.java) and also to 
understand how you can easily make plugins of other types for other modules. 

To use this for other new plugin types, you need to define a new Interface that extends [CommonPluginInterface](https://github.com/PAMGuard/PAMGuard/blob/main/src/PamModel/CommonPluginInterface.java)
with the new interface having a function to create whatever class will be used by PAMGuard with whatever arguments are needed in the call to it's constructor. You then add
a [PluginLoader](https://github.com/PAMGuard/PAMGuard/blob/main/src/PamModel/PluginLoader.java) in the constructor of the module that will use the plugin(s) and loop through any 
discovered plugin interfaces to create the required classes. Look in this project, and in [AnalogDevicesManager](https://github.com/PAMGuard/PAMGuard/blob/main/src/analoginput/AnalogDevicesManager.java) 
for examples of how this all works. 
