# agentic cli rag proj
this repo is a benchmark harness to compare retrieval-augmented generation (RAG) strategies for a local, code-assisting agentic CLI tool. 
the goal is to see which retrieval method gives a coding agent the highest task success rate while retrieving the correct files and passing as many criteria as possible.


## download local models
1. download ollama thru terminal or from [ollama](https://ollama.com/download) website
2. run `ollama pull qwen2.5-coder:7b` in terminal to download the generation model  
3. run `ollama pull qwen3-embedding:0.6b` in terminal to download the embedding model

## clone repo
1. clone repo
2. install requirements `python3 -m pip install -r requirements.txt`
3. make sure that ollama downloaded everything correctly `ollama run qwen2.5-coder:7b`

## get your knowledge base together or use mine
1. choose 8-10 of your personal projects
2. scrub files of all personal info and api keys
3. remove all essential files (ide files, documentation, proc/dockerfiles, images/video/audio files, non-essential .json files, licenses, cache, output files, etc)

## running scripts (run these in order!!)
1. chunk knowledge base: `python3 chunker.py`
2. build indexes: `python3 build_index.py`
3. test keyword retrieval: `python3 bm25_search.py`<br>
run this if it errors out: `pip install rank_bm25 --break-system-packages`
4. build embeddings: `python3 build_embeddings.py`
run this if it errors out: `pip install chromadb ollama --break-system-packages`
5. test semantic retrieval: `python3 embedding_search.py`

## when you're ready to start testing
`run_harness.py` - runs every task outlined in the manifest file under all 3 conditions with a human judged retry loop
-  can be stopped and resumed
-  data written to results.jsonl

## grading and results
`grade_results.py` - interactive grading pass for the results file
`summarize_results.py` - creates tables based on results and writes them to results_summary.md

