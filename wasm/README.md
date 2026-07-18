# asobi WASM boundary

The canonical actor implementation is the CLJC source under `src/asobi/`.
`kotoba.app.edn` points at `src/asobi/mesh.clj` for mesh component compilation.

Go/TinyGo and the former Python/componentize-py plan are deprecated. Any generated
WASM artifact is a build output, never canonical source or metadata.
