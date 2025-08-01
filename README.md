# prima-repo-test

> Mia prima repo: la uso come test per provare un po' di cose su Git e GitHub.

Questo è il mio README, e lo userò anche come **mini tutorial personale** per ricordarmi come fare operazioni importanti con Git.  
Ogni volta che imparo qualcosa di nuovo, lo aggiungo qui sotto 

---

## Indice

- [Contenuto della repo](#contenuto-della-repo)
- [Comandi Git che ho imparato](#comandi-git-che-ho-imparato)
  - [Primo setup](#primo-setup)
  - [Clonare una repo](#clonare-una-repo)
  - [Ciclo modifica → commit → push](#ciclo-modifica--commit--push)
  - [Errore: Push rifiutato perché il branch remoto è più aggiornato](#errore-push-rifiutato-perché-il-branch-remoto-è-più-aggiornato)
  - [Gestione dei branch](#gestione-dei-branch)
  - [Usare lo stash](#usare-lo-stash)
  - [Creare un tag e una release](#creare-un-tag-e-una-release)
  - [Aggiornare release](#aggiornare-release)
  - [Annullare modifiche: `git restore` e `git reset`](#annullare-modifiche-git-restore-e-git-reset)
  - [Applicare un singolo commit: `git cherry-pick`](#applicare-un-singolo-commit-git-cherry-pick)
  - [Annullare un commit pubblico: `git revert`](#annullare-un-commit-pubblico-git-revert)
  - [Trovare il commit che ha introdotto un bug: `git bisect`](#trovare-il-commit-che-ha-introdotto-un-bug-git-bisect)
- [To-do personali](#to-do-personali)
- [LICENSE (MIT)](#license-mit)
- [CHANGELOG](#changelog)


---

##  Contenuto della repo

- `src/` – codice sorgente (es. file Scala)
- `docs/` – documentazione tecnica o schema dati
- `test/` – eventuali file di test
- `.gitignore` – per escludere file inutili
- `CHANGELOG.md` – cronologia delle modifiche
- `LICENSE` – licenza del progetto

---

##  Comandi Git che ho imparato

### Primo setup

```bash
git config --global user.name "Giacomo Danese"
git config --global user.email "giacomodanese7@gmail.com"
```

---

### Clonare una repo

```bash
git clone https://github.com/giekbreck/prima-repo-test.git
```

---

### Ciclo modifica → commit → push

```bash
git status
git add <file>
git commit -m "Messaggio"
git push origin main
```

---

### Errore: Push rifiutato perché il branch remoto è più aggiornato

Se quando fai `git push origin main` ottieni questo errore:
```bash
! [rejected] main -> main (fetch first)
error: failed to push some refs to ...
hint: Updates were rejected because the remote contains work that you do not have locally.
```

Questo significa che il branch su GitHub contiene dei commit che non sono ancora presenti nel tuo repository locale..

#### Consiglio: quando lavori su più dispositivi o anche via web, fai sempre un pull prima di un push:

```bash
git pull origin main --rebase
git push origin main
```

---

###  Gestione dei branch

```bash
git checkout -b nuovo-branch
git switch main
git merge altro-branch
```

---

### Usare lo stash

```bash
git stash
git stash list
git stash apply
git stash pop
```

---

### Creare un tag e una release

```bash
git tag -a v1.0 -m "Prima versione stabile"
git push origin v1.0
```

---

### Aggiornare release

-Vai nella sezione 'Releases' sulla tua pagina GitHub

-Clicca su “Draft a new release”

-Scegli il tag v1.1

-Titolo: Versione 1.1 – Notebook + README

-Corpo: copia ciò che hai scritto nel changelog

-Pubblica cliccando "Publish release"

---

### Annullare modifiche: `git restore` e `git reset`

#### `git restore`

Annulla modifiche non ancora aggiunte allo staging.  
Esempio: hai modificato un file ma vuoi tornare alla versione salvata nel repo.

```bash
git restore nomefile.ext
```

#### 'git reset'

Hai già lanciato git add ma vuoi tornare indietro:

```bash
git add nomefile.ext      # aggiunge il file al commit
git reset nomefile.ext    # lo rimuove dal commit, ma NON cancella la modifica

```

#### git reset con HEAD

| Comando                    | Cosa fa                                                    |
| -------------------------- | ---------------------------------------------------------- |
| `git reset --soft HEAD~1`  | Annulla l'ultimo commit, ma **mantiene i file in staging** |
| `git reset --mixed HEAD~1` | Annulla il commit e **rimuove i file dallo staging**       |
| `git reset --hard HEAD~1`  | Annulla tutto e **cancella anche le modifiche locali**     |

Attenzione: --hard è distruttivo. Usalo solo se sei sicuro.

---

### Applicare un singolo commit: `git cherry-pick`

Serve per prendere un singolo commit da un altro branch e applicarlo al branch attuale senza fare un merge completo.

#### Esempio d’uso

```bash
# 1. Crea un nuovo branch e fai un commit:
git checkout -b cherry-branch
echo "// file per cherry-pick" > src/file_cherry.scala
git add src/file_cherry.scala
git commit -m "feat: aggiunto file per testare cherry-pick"

# 2. Torna su main
git checkout main

# 3. Trova l'hash del commit da cherry-pick
git log cherry-branch --oneline

# 4. copia il codice dell'hash corrispondente al commit (in questo caso era 'acd6cc3') 

# 5. Applica solo quel commit su main
git cherry-pick acd6cc3

```

---

### Annullare un commit pubblico: `git revert`

Il comando `git revert` serve per annullare un commit precedente ma senza cancellarlo dalla cronologia.

Git crea un nuovo commit "inverso" che annulla gli effetti del commit originale.

È il metodo sicuro da usare su branch pubblici (come `main`), perché non riscrive la cronologia

```bash
# 1. Trova l'hash del commit da annullare
git log --oneline

# 2. Esegui il revert (Git aprirà l’editor per il messaggio)
git revert <commit-hash>

```

Dopo averlo lanciato si aprirà sul terminale una finestra per inserire un messaggio relativo al commit creato da git revert.
Scrivi il messaggio e per chiudere la finestra premi tasto esc e digita :wq, altrimento :!q per uscire senza salvare

---


### Trovare il commit che ha introdotto un bug: `git bisect`

Il comando `git bisect` ti aiuta a scoprire esattamente quale commit ha introdotto un bug o un errore.  
Funziona come una ricerca binaria: Git ti fa testare volta per volta commit intermedi finché non individua il colpevole.


#### Come funziona:

1. **Avvia la modalità bisect**
   ```bash
   git bisect start
   ```
   
2. **Per prima cosa dichiarare il commit 'cattivo' (sono iniziati gli errori)**
   ```bash
   git bisect bad
   ```

3. **Dichiara il commit buono (l'ultimo in cui sai che ancora funzionava)**
   ```bash
   git bisect good <inserisci numero-hash oppure tag>
   ```

4. A questo punto Git fa un check per trovare un commit intermedio tra il good ed il bad
   D'ora in poi sei tu che devi verificare se ti trovi ad un commit che abbia dei bug o meno.

5. Quindi volta per volta scrivi:
   ```bash
   git bisect good

   #oppure

   git bisect bad
   ```

6. Vai avanti così fino a quando non trovi l'ultimo commit buono (o il primo non funzionante)

7.Ora chiudi e torna al branch:
   ```bash
   git bisect reset
   ```

**ESEMPIO:**

# Avvio della ricerca del bug
git bisect start
git bisect bad                 # L’ultimo commit con il bug
git bisect good HEAD~3         # Commit noto funzionante (al posto di HEAD~3 puoi mettere il numero di hash del commit)

# Dopo vari test...
git bisect good                # Se il commit testato funziona
git bisect bad                 # Se il commit testato è rotto

# Risultato finale
# Git ti dice: "commit XYZ è il primo cattivo"

git bisect reset               # Torna al branch principale


---

## To-do personali

---

## LICENSE (MIT)

MIT License

Copyright (c) 2025 Giacomo Danese

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

---

## CHANGELOG


#### v1.0 – 2025-07-31
Prima versione stabile del progetto

-Aggiunta struttura base (src, docs, test)

-Creato .gitignore

-Primo file Scala spostato in src/

-Creato tag v1.0 e release associata


#### v1.1 - 2025-08-01
-Aggiunto notebook "test_git.ipynb" nella cartella src/

-Aggiunta sezione "Errore push rifiutato" e "Aggiornare release" nel README

-Aggiornamento struttura e documentazione


