#include "include/linfo_pl/linfo_pl_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "linfo_pl_plugin.h"

void LinfoPlPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  linfo_pl::LinfoPlPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
