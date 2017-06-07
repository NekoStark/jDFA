# jDFA
[![Build Status](https://travis-ci.org/NekoStark/jDFA.svg?branch=master)](https://travis-ci.org/NekoStark/jDFA)
[![codecov](https://codecov.io/gh/NekoStark/jDFA/branch/master/graph/badge.svg)](https://codecov.io/gh/NekoStark/jDFA)
[![Issue Count](https://codeclimate.com/github/NekoStark/jDFA/badges/issue_count.svg)](https://codeclimate.com/github/NekoStark/jDFA)

 Deterministic Finite Automata implementation in Java (**W.I.P.**) for Theoretical Computer Science class, 2017, UniFi.

#### instructions
1. clone the project and move inside the root folder
2. build the project with maven
3. run the jar produced by the **jdfa-runner** project
```
java -jar jdfa-runner/target/jdfa-runner.jar [operation] [args..]
```
  - first parameter is either **acceptance** or **minimize**. In the first case, is
    possibile to test a series of strings against a defined DFA. In the second  
    case is possibile to calculate the minimized DFA from a initial definition.
  - the subsequent parameters can be
    - **def="..."** : a dfa definition, required in both operations
    - **strings="..."** : a comma separated list of strings, to use in the *acceptance* operation
