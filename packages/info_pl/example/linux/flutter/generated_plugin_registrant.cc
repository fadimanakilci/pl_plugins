//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <info_pl/info_pl_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) info_pl_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "InfoPlPlugin");
  info_pl_plugin_register_with_registrar(info_pl_registrar);
}
