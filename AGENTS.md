# AGENTS.md

## Repository Overview

This repo holds MOSIP's performance test scripts and related assets. It is
mostly a collection of Apache JMeter test plans (`.jmx` files), sample test
data, PDF performance reports, and a handful of small standalone Java
utilities — not a single deployable application. There is no top-level build
that ties everything together; each module folder is tested and run on its
own.

The modules covered (per the root `README.md`) are:

1. Pre-registration (UI and batch jobs)
2. Registration Processor
3. ID Repository
4. ID Authentication
5. Kernel
6. Resident services

Tools used: [Apache JMeter](https://jmeter.apache.org/) for load scripts and
[Glowroot](https://glowroot.org/) for profiling.

Note: several existing per-module `README.md` files link to files under
`https://github.com/mosip/mosip-performance-tests-mt/...` (a sibling repo)
rather than to files inside this repo. Treat those links as pointers to that
other repo, not as guaranteed-accurate links into this one — verify a link's
target before relying on it.

## Technology Stack

- **Apache JMeter `.jmx` scripts** — the primary test artifacts, under each
  module's `scripts/` folder.
- **Java + Maven** — a few standalone utilities under `utilities/`:
  - `utilities/modifyfile` (plain Maven, Java 8, no dependencies)
  - `utilities/pridvolume_utility` (Spring Boot 2.1.7, Java 8, Postgres,
    Spring Data JPA)
  - `utilities/registrationprocessor_sync_utility` (Spring Boot, depends on
    MOSIP registration-processor artifacts)
  - `utilities/regproc_transactiondata_util_v2.2` (Maven, has its own
    `README.md`)
  - `utilities/regprocessorpacketgenutil` (Maven, Java 8, depends on
    `kernel-core` / `registration-processor-*` 1.0.2 artifacts)
- **Helm chart** — `utilities/java-profiler-agent` is a Helm chart (not a
  Java project) used to attach a profiler to a running Kubernetes pod. It has
  its own `Chart.yaml`, `values.yaml`, `templates/`, and `README.md`.
- **No CI workflows** — there is no `.github/workflows` directory in this
  repo, so nothing here is validated automatically by GitHub Actions. Do not
  claim a change is "covered by CI" — it isn't.
- **No root LICENSE file** — the README references the MPL 2.0 license from
  `mosip-platform`, but no `LICENSE` file is committed in this repo.

## Build & Test Commands

There is no root `pom.xml` or build script. Each Maven utility is built from
inside its own folder. The two examples below are illustrative — apply the
same `cd` into the folder, then `mvn clean install` pattern for the other
Maven utilities listed above (`modifyfile`, `registrationprocessor_sync_utility`,
`regproc_transactiondata_util_v2.2`):

```shell
cd utilities/pridvolume_utility
mvn clean install
```

```shell
cd utilities/regprocessorpacketgenutil
mvn clean install
```

If you need to run a built utility jar with a system property, put `-D`
options **before** `-jar`, e.g.:

```shell
java -Dspring.profiles.active=default -jar target/PridVolumeUtility-0.0.1-SNAPSHOT.jar
```

There is no repo-wide or root-level test suite, and most of this repo isn't
covered by automated tests. One exception: `utilities/regproc_transactiondata_util_v2.2`
has JUnit tests under `src/test/java/dbtests/` (`DBTest.java`,
`DataProcessorTest.java`) — run those with `mvn test` inside that utility's
own folder if you change it. Everywhere else, "testing" means running JMeter
scripts against a deployed MOSIP environment (sandbox, dev, QA, or a local
Kubernetes setup), not `mvn test`.

To run a JMeter script:

```shell
# from the JMeter bin/ directory, GUI mode (for validating with 1 user)
./jmeter.sh -t "/path/to/repo/id-authentication/scripts/IDA_Test_script.jmx"

# non-GUI mode (for an actual load run)
./jmeter.sh -n -t "/path/to/repo/id-authentication/scripts/IDA_Test_script.jmx" -l results.jtl
```

General flow described in the root `README.md`:

1. Download and install Apache JMeter.
2. Open the `.jmx` script for the module you're testing.
3. Validate the script with a single user.
4. Do a 10-minute dry run.
5. Run the real load test at the required load levels.

## Configuration

- `application.properties` (repo root) — Spring/MOSIP test-harness settings:
  base URLs (`mosip.test.baseurl`), registration client IDs, and **plaintext
  values that look like credentials** (e.g. `mosip.test.regclient.password`,
  `mosip.test.regclient.secretkey`, `mosip.test.print.event.secret`). These
  match MOSIP's well-known public sandbox/dev defaults used throughout the
  MOSIP codebase — these are non-production placeholders, but they remain
  authentication material and should be treated as sensitive: **never
  replace these placeholder values with a real environment's credentials
  and commit the result.** If you need to
  point a script at your own environment, override the value locally and
  keep the change out of your commit/PR.
- `default.properties` (repo root) — pre-registration test defaults
  (`urlBase`, `operatorId`, `password`, `clientId`, `secretKey`, OTP
  settings, etc.). Same caveat as above: these are sandbox defaults, not
  something to overwrite with real secrets and commit.
- Each module's `support-files/` folder holds CSV/TXT files that parameterize
  its scripts (center/machine IDs, identity request bodies, credential
  request variables, document names, etc.). Module `README.md` files
  describe which file drives which scenario — check the module's own
  `README.md` before changing one of these.
- `utilities/java-profiler-agent/values.yaml` — Helm values for the profiler
  chart, including `profileAppService`. This is the pod's `app` label value
  used by the chart's Service selector (`templates/java-profiler-svc.yaml`)
  to target the pod to profile — not a Kubernetes Service name or endpoint
  address. This is edited per-environment, not committed with
  environment-specific values baked in.

## Project Structure Notes

```text
.
├── commons/
│   ├── credential/{README.md,scripts,support-files}
│   ├── data-share/{README.md,scripts,support-files}
│   ├── id-repository/{README.md,reports,scripts,support-files}
│   ├── kernel/{README.md,reports,scripts,support-files}
│   ├── masterdata/{README.md,scripts,support-files}
│   └── packetmanager/{README.md,scripts,support-files}
├── id-authentication/{README.md,reports,scripts,support-files}
├── pre-registration/{README.md,reports,scripts,support-files}
├── registration/
│   ├── registration/README.md
│   └── registrationprocessor/{README.md,reports,scripts,support-files}
├── resident-services/{README.md,reports,scripts,support-files}
├── testplan/{README.md, test plan spreadsheet}
├── utilities/
│   ├── java-profiler-agent/   (Helm chart)
│   ├── modifyfile/            (Maven)
│   ├── pridvolume_utility/    (Maven/Spring Boot)
│   ├── registrationprocessor_sync_utility/ (Maven/Spring Boot)
│   ├── regproc_transactiondata_util_v2.2/  (Maven)
│   └── regprocessorpacketgenutil/          (Maven)
├── githooks/                  (commit-msg hook enforcing MOS-xxxx / merge commit format)
├── application.properties
├── default.properties
└── README.md
```

Every module folder that has JMeter scripts follows the same layout:
`README.md` (environment prerequisites + how to run), `scripts/` (`.jmx`
files), `support-files/` (test data), and `reports/` (past performance test
PDF reports — historical output, not something to edit). Read a module's own
`README.md` before touching its scripts; it lists the exact MOSIP services
that must be running in the target environment for that script to work.

A single root `AGENTS.md` is used here (no per-module `AGENTS.md` files)
because the JMeter module folders have no independent build/CI tooling of
their own beyond a `README.md` and static `.jmx`/data files — the only real
"build" concern in the repo is the handful of Maven utilities under
`utilities/`, and those already have adequate module-level `README.md`
documentation to point to.

## Development Workflow

1. Fork and clone this repo; add `upstream` pointing at
   `https://github.com/mosip/performance-test-scripts.git`.
2. Branch from the current default branch, `master` (verify with
   `gh repo view mosip/performance-test-scripts --json defaultBranchRef`
   before assuming — MOSIP repos vary between `master` and `develop`).
3. This repo enforces a commit-message hook (see `githooks/README.MD`):
   commits must start with `MOS-xxxx` (a Mantis/Jira-style ticket ID) or be a
   merge commit. Enable it once per clone with:

   ```shell
   git config core.hooksPath githooks/
   ```

4. Keep each change scoped to one related module, utility, or
   repository-level objective (e.g. this `AGENTS.md`) — don't mix changes
   across unrelated modules (e.g. `id-authentication/` and
   `commons/kernel/`) in one commit unless they're genuinely related.
5. If you add or edit a `.jmx` script, validate it can open in JMeter and
   run for a single user before submitting — there's no automated check for
   this.
6. If you edit a Maven utility under `utilities/`, run `mvn clean install`
   inside that utility's own folder to confirm it still builds.

## Pull Request Guidelines

- Commit messages must start with a ticket ID in `MOS-xxxx` format (per the
  githooks commit-msg hook), except for merge commits.
- Keep PRs scoped to one module/utility where possible — this makes review
  easier since modules are independent of each other.
- Do not commit real environment credentials, tokens, or URLs into
  `application.properties`, `default.properties`, or any `support-files/*`
  CSV — keep those as sandbox/placeholder values.
- Do not commit new binary performance reports (PDFs) as part of a code or
  script change; report PDFs are historical records, not review artifacts.
- There is no CI here, so reviewers rely on the PR description to know what
  was tested and how (JMeter version used, environment run against, number
  of users/duration for a dry run, etc.) — include that in the PR body.

## Repository-Specific Considerations

- This repo is a grab-bag of independent module folders, not a single
  product. Changes are almost always local to one module folder.
- `.jmx` files are XML and can be large — prefer opening them in JMeter's
  GUI to inspect/edit rather than hand-editing the XML unless you know
  exactly which element you're changing.
- The `reports/` folders under each module contain past PDF performance
  reports (some `reports/` folders literally contain a `No Reports.txt`
  placeholder, e.g. `resident-services/reports/`). These are historical
  artifacts — don't treat their presence/absence as a build signal.
- Module `README.md` files list the specific MOSIP Kubernetes services that
  must be up before a script will work (e.g. `commons/id-repository`
  requires Websub, kernel notification/audit/authmanager, biosdk, and all
  IDrepo services). Missing a prerequisite service is the most common reason
  a script fails, not a script bug.
- `utilities/java-profiler-agent` is a Helm chart, not a Java project —
  don't try to `mvn build` it. Its `README.md` documents the manual steps to
  install it against a running console/environment machine.

## Agent rules

### Do

1. Read the target module's own `README.md` before writing or running a
   `.jmx` script, and confirm which Kubernetes services it expects.
2. Keep changes scoped to a single module, utility folder, or
   repository-level objective (e.g. this `AGENTS.md`) per PR.
3. Use placeholder/sandbox-style values in `application.properties`,
   `default.properties`, and `support-files/*` — matching the existing
   convention in those files.
4. Run `mvn clean install` inside a utility's own folder after changing its
   Java source, and confirm it succeeds.
5. Follow the `MOS-xxxx` commit-message convention once the `githooks`
   commit-msg hook is enabled locally.
6. Verify the actual default branch (`master` at present) before branching,
   rather than assuming `develop`.

### Do not

1. Do not commit real credentials, tokens, or internal URLs into any
   properties file or `support-files/*` CSV/TXT file.
2. Do not claim a change is validated by CI — this repo has no
   `.github/workflows`.
3. Do not mix edits across unrelated module folders in a single commit or
   PR.
4. Do not hand-edit `.jmx` XML for anything beyond a small, well-understood
   change — use the JMeter GUI to make and verify the change instead.
5. Do not put `-D` system properties after `-jar` in Java run commands — the
   JVM ignores them there.
6. Do not add new PDF performance reports as part of an unrelated code or
   script change.
