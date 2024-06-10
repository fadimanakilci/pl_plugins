//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <linfo_pl/linfo_pl_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) linfo_pl_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "LinfoPlPlugin");
  linfo_pl_plugin_register_with_registrar(linfo_pl_registrar);
}
