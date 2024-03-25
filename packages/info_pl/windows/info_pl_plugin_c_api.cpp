#include "include/info_pl/info_pl_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "info_pl_plugin.h"

void InfoPlPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  info_pl::InfoPlPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
