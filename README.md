# Connect Five


This is a variation on the Connect Four game. To win in this version connect five tiles in a vertical, hortizontal, accending or descending lines!

## Overview

The game is implemented as a client-server solution.
The Connect Five Server uses websockets technology to receive and publish events on three endpoints:
- A `/topic/games/` topic that provides all the information related to games
- A specific game topic`/topic/games/{gameId }` that provides information related to a specific game
- A user queue `/user/queue/game` that provides user specific events

There are three modules making up the implementation:
- `connect-five-events` - Java Implementation of shared events
- `connect-five-server` - Java Implementation of the Connect Five Server
- `connect-five-client` - Java Implementation of the Connect Five Client

## Assumptions
- Only two users connect at any one time. While the server and client have been designed to support multi game sessions, further work would be required to make this completely work.
- The algorithm to solve tile connects is very simple. A design is in place that allows that algorithm to be swapped with something more efficient. For example, there are other approaches to solving tile connects like the use of bitmasks, or graph traversal solutions.
- The server is a single instance only. The design allows for swapping the currentin memory implementation of the game data with an external data store for example, database or redis server.

## Build

- The project uses Maven https://maven.apache.org/ to build and manage dependencies.  
- The project build was tested with Maven 3.6.0 and Java 1.8.

To build: `mvn clean install`

To run the server: 
- `cd connect-five-server`
- `java -jar target/connect-five-server-0.0.1-SNAPSHOT.jar`

To run a client: 
- `cd connect-five-client`
- `java -jar target/connect-five-client-0.0.1-SNAPSHOT.jar`


## Design

### Websockets

The WebSocket protocol allows you to implement bidirectional communication between applications.
In this case websockets are used between the client and server. HTTP is used for the initial handshake.

The WebSocket protocol is a low-level protocol. It defines how a stream of bytes is transformed into frames.
In this implementation I am using Spring Boot implementation of websockets, and using the STOMP sub-protocol to control communication of the websocket frames.

The server can recieve messages over websocket channels from the client, and respond with messages.
In the Connect Five implementation a `GameEvent` is sent and received over all channels, containing different "action" depending on the outcome. For example, when a player, wants to start a game, a `GameEvent` with an `InitiateGameActionRequest` action is sent to the server on the `/topic/games/` topic. The server repiles with a `GameOpenActionResponse` action to the client. The client can decide what happens on each response, for example, in this case, the client waits for more players to join. 

### Event Overview

| Channel   | Endpoint                |Request                        | Response                          |
|-----------|-------------------------|-------------------------------|-----------------------------------|
| General   |`/topic/games/`          | `InitiateGameActionRequest`   | `GameOpenActionResponse`          |
|           |                         | `JoinGameActionRequest`       | `ReadyGameActionResponse`         |
|           |                         | `JoinGameActionRequest`       | `UnavailableGameActionResponse`   |
|           |                         |                               | `GameDeletedActionResponse`       |
| Game Only |`/topic/games/{gameId}`  |                               | `BoardStatusGameActionResponse`   | 
|           |                         | `CompletedGameActionRequest`  | `GameDeletedActionResponse`       |
| User Only |`/user/queue/game`       | `MakeMoveGameActionRequest`   | `CompletedMoveGameActionResponse` | 
|           |                         |                               | `InvalidGameActionResponse`       | 







