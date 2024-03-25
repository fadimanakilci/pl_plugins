#ifndef FLUTTER_PLUGIN_INFO_PL_PLUGIN_H_
#define FLUTTER_PLUGIN_INFO_PL_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace info_pl {

class InfoPlPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  InfoPlPlugin();

  virtual ~InfoPlPlugin();

  // Disallow copy and assign.
  InfoPlPlugin(const InfoPlPlugin&) = delete;
  InfoPlPlugin& operator=(const InfoPlPlugin&) = delete;

  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace info_pl

#endif  // FLUTTER_PLUGIN_INFO_PL_PLUGIN_H_
