# DroidMentor — Project Plan

## Division of work

| | Feature | Owns | Learns |
|---|---|---|---|
| **A** | Chat with the mentor | Active Chat screen, state machine, payload construction, Gemini/Ktor client, network check, base error handling | Compose, ViewModel, Ktor + Serialization |
| **B** | Conversation history | Chat History screen, new/delete conversation, message persistence, resume last conversation on launch | Compose, ViewModel, Room + DataStore |
| **C** | App shell & configuration | Application/container, navigation, Title, About, Settings (API key), PT/EN strings, orientations | Compose, ViewModel, navigation, DataStore (+ Keystore) |

## Timeline

### Shared kickoff (now → 21/09)
- All three: agree on the domain model and interfaces (`ConversationRepository`, `ChatService`, `SettingsRepository`) — the single coupling point between features.
- Deliverable (**Milestone 1**, tag `mentor_1`, due 21/09): this plan + README with member names, pushed to the repo, access granted to both professors.

### 21/09 → 28/09
- **C** delivers the app shell: navigation graph, Application container, empty screens.
- **A** and **B** build their own screens as stateless composables with previews, without waiting on C's shell to be finished.

### 28/09 → 12/10 (Milestone 2)
- **A**: Active Chat with a fake/in-memory `ChatService` implementation (no Ktor yet), payload-building and truncation logic covered by domain tests.
- **B**: Chat History with in-memory repository, save/delete/list working, domain tests for persistence logic.
- **C**: navigation and settings screens fully wired to fake/in-memory repositories; PT/EN strings in place.
- Deliverable: tag `mentor_2` — 5–7 min video (features so far, key decisions, testing strategy, current status) linked in the README. No Ktor/Room yet — that's expected at this point in the course.

### 12/10 → 16/11 (Milestone 3)
- **A**: swap the fake `ChatService` for the real Ktor client — request/response DTOs, error mapping (429/500/timeout/no network).
- **B**: swap the in-memory repository for Room, wire DataStore for the API key and the active-conversation id (decide now who owns that DataStore call — it sits at the boundary between B's and C's features).
- **C**: integrate the container so A and B's real implementations are injected app-wide; finish orientation handling.
- Deliverable: tag `mentor_3` — video, same structure minus the testing-strategy section.

### 16/11 → 12/12 (Final milestone, with rotation)
- **A**: images — gallery/camera, local file storage, multimodal part in the request, image path stored (never raw bytes) in Room.
- **B**: conversation rewrite — edit a message at any position, delete everything after it, re-call the model with the truncated history.
- **C**: resilience & quality — error UX (429/500/timeout), offline banner, logging, tests with `MockEngine` and in-memory Room, UI tests.
- Deliverable: tag `mentor_f` — final 5–7 min demo video, all features integrated and working together.

## Ground rules
- Keep `imagePath` nullable in Room from the start, to avoid a migration once images land.
- Persist the user's message before calling the API, so it isn't lost if the ViewModel dies.
- Never send error messages back into the history sent to the model.
- Every PR is reviewed by someone unfamiliar with that layer — review doubles as teaching.
- `Application` and the navigation graph are shared files: small, frequent changes only.