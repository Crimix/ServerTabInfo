# Server Tab Info Changelog
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),

## [1.16.1, 1.3.1-b1] - 2020-08-13
- Update to 1.16.2 as a beta version.
- Will be in beta until some more Forge updates and mappings have been released.

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