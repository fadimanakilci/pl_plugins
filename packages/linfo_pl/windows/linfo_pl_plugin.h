#ifndef FLUTTER_PLUGIN_LINFO_PL_PLUGIN_H_
#define FLUTTER_PLUGIN_LINFO_PL_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace linfo_pl {

class LinfoPlPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  LinfoPlPlugin();

  virtual ~LinfoPlPlugin();

  // Disallow copy and assign.
  LinfoPlPlugin(const LinfoPlPlugin&) = delete;
  LinfoPlPlugin& operator=(const LinfoPlPlugin&) = delete;

  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace linfo_pl

#endif  // FLUTTER_PLUGIN_LINFO_PL_PLUGIN_H_
