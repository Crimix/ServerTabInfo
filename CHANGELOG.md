# Server Tab Info Changelog
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

## [1.17.1, 1.3.4-b1] - 2021-07-25
- Port to 1.17.1 
- Will be in beta until full Forge release, expect things to break.
- Added more control over refresh rate [#14](https://github.com/Crimix/ServerTabInfo/issues/14)

## [1.16.4, 1.3.3] - 2020-11-20
- Full stable release
- Partial support for [#12](https://github.com/Crimix/ServerTabInfo/issues/12)  
Now an integer based scoreboard objective can be displayed in the player tab list between the dimension tps info and ping.  
For now there is no support for heart based scores.

## [1.16.4, 1.3.3-b1] - 2020-11-04
- Port to 1.16.4
- Will be in beta until some more Forge updates and mappings have been released.
- Added admin only mode [Fixes #9](https://github.com/Crimix/ServerTabInfo/issues/9)  
It requires the user to have a op level greater than or equal to 1.  
I recommend the [Permissions Levels](https://www.curseforge.com/minecraft/mc-mods/permission-levels) to op players to only level 1.

## [1.16.3, 1.3.3] - 2020-09-23
- Fixed and issue which caused the client to be rejected if the mod was not installed.
- Restored proper behaviour of the mod not needed on the server or client.

## [1.16.3, 1.3.2] - 2020-09-12
- Port to 1.16.3

## [1.16.2, 1.3.3] - 2020-09-23
- Fixed and issue which caused the client to be rejected if the mod was not installed.
- Restored proper behaviour of the mod not needed on the server or client.

## [1.16.2, 1.3.2] - 2020-09-11
- Added more russian translations (Thanks DrHesperus)

## [1.16.2, 1.3.1] - 2020-09-05
- First 1.16.2 release
- Updated to Forge version 33.0.37

## [1.16.2, 1.3.1-b1] - 2020-08-13
- Update to 1.16.2 as a beta version.
- Will be in beta until some more Forge updates and mappings have been released.

## [1.16.1, 1.3.3] - 2020-09-23
- Fixed and issue which caused the client to be rejected if the mod was not installed.
- Restored proper behaviour of the mod not needed on the server or client.

## [1.16.1, 1.3.2] - 2020-09-11
- Added more russian translations (Thanks DrHesperus)

## [1.16.1, 1.3.1] - 2020-08-13
- Fixes crashes on servers [Fixes #7](https://github.com/Crimix/ServerTabInfo/issues/7)
- Fixes overlay issues when on servers.
- Upgraded internal BML version to 1.1.0. 

## [1.16.1, 1.3.0] - 2020-08-10
- Changes the way overlay lists are drawn.
- Removed ping from dimension list, it does not make sense to keep when it can be seen on the player list.
- Cleaned up the keybinds.
- Colors are now back on the overlays.
- Changed how tps information is exchanged. 
    * The server wil now at regular intervals when there are online players, calculate tps, cache it and send it to players.
    * The interval is configurable from every 100 ticks to every 600 ticks, default is 100 ticks.
    * When a new player connects to the server, it will send the cached version to that player.
- Switched to using DataGenerators for translations.
- Internal shadowed use of BML.
- Waila compatibility re-added (HWYLA).
- Added zh_cn translations (Thanks BlueskyClouds)

## [1.16.1, 1.2.7-alpha] - 2020-07-05
- Ported the mod to 1.16
- I cannot guaranty that it works as intended, use at your own risk
- Colors are broken, but it runs
- It does not have Waila compatibility yet (HWYLA is not updated yet)

## [1.15.2, 1.2.7] - 2020-07-04
- Switch to use this changelog format.
- Update Forge to 31.1.25
- Updated mappings
- Refactored code
- Implemented Waila compatibility [Fixes #5](https://github.com/Crimix/ServerTabInfo/issues/5)

# Example
## [MC-VERSION, VERSION] - Date of release (YYYY-MM-DD)
### Added
- 
### Changed
- 
### Deprecated
- 
### Removed
- 
### Fixed
- 
### Security
- 