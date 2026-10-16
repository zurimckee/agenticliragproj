# Results Summary

Runs logged: 45/45 · ungraded: 0

## By retrieval method

| Method | Runs | Graded | Success rate | Avg. retries | Avg. time / task | Hit retry cap | Retrieved target file |
|---|---|---|---|---|---|---|---|
| baseline | 15/15 | 15/15 | 1/15 (7%) | 0.00 | 123.65 s | 0 | — |
| bm25 | 15/15 | 15/15 | 2/15 (13%) | 0.00 | 477.70 s | 0 | 10/15 (67%) |
| embedding | 15/15 | 15/15 | 3/15 (20%) | 0.07 | 370.92 s | 0 | 13/15 (87%) |

## Success rate by difficulty

| Difficulty | baseline | bm25 | embedding |
|---|---|---|---|
| easy | 1/5 (20%), 0.00 retries | 2/5 (40%), 0.00 retries | 2/5 (40%), 0.20 retries |
| medium | 0/5 (0%), 0.00 retries | 0/5 (0%), 0.00 retries | 0/5 (0%), 0.00 retries |
| hard | 0/5 (0%), 0.00 retries | 0/5 (0%), 0.00 retries | 1/5 (20%), 0.00 retries |

## Success rate by prompt style

| Prompt style | baseline | bm25 | embedding |
|---|---|---|---|
| keyword | 0/8 (0%), 0.00 retries | 1/8 (12%), 0.00 retries | 1/8 (12%), 0.00 retries |
| paraphrased | 1/7 (14%), 0.00 retries | 1/7 (14%), 0.00 retries | 2/7 (29%), 0.14 retries |

_Retries = attempts_used − 1. Time = model generation time summed over all attempts (excludes review time)._
