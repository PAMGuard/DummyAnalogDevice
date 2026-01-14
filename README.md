---
editor_options: 
  markdown: 
    wrap: 72
---

# Dummy Analog Device Plugin

Version 2.02.18 of PAMGuard includes a more flexible plugin loader which
can more easily be used with multiple different plugin types. This has
been implemented in the
[AnalogDevicesManager](https://github.com/PAMGuard/PAMGuard/blob/main/src/analoginput/AnalogDevicesManager.java)
to make it easy to incorporate new, and possible very platform specific,
analog sensor inputs. This project can be used as an example of how to
make your own
[AnalogDevicePlugin](https://github.com/PAMGuard/PAMGuard/blob/main/src/analoginput/AnalogDevicePlugin.java)
and also to understand how you can easily make plugins of other types
for other modules.


Generally, to use this, you need to define a new Interface that extends
[CommonPluginInterface](https://github.com/PAMGuard/PAMGuard/blob/main/src/PamModel/CommonPluginInterface.java)
with the new interface having a function to create whatever class will
be used by PAMGuard with whatever arguments are needed in the call to
it's constructor. You then add code in the constructor of the module
that will use this type of plugin that will ask PamModel for a list of
plugins of that class and load the plugins with whatever command and
parameters is appropriate for that plugin type. See
[AnalolgDeviceManager](https://github.com/PAMGuard/PAMGuard/blob/main/src/analoginput/AnalogDevicesManager.java)
and
[AcquisitionControl](https://github.com/PAMGuard/PAMGuard/blob/main/src/Acquisition/AcquisitionControl.java)
for examples.
