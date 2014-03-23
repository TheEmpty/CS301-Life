;Copyright (c) 2014 Mohammad El-Abid
;
;Permission is hereby granted, free of charge, to any person obtaining a copy
;of this software and associated documentation files (the "Software"), to deal
;in the Software without restriction, including without limitation the rights
;to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
;copies of the Software, and to permit persons to whom the Software is
;furnished to do so, subject to the following conditions:
;
;The above copyright notice and this permission notice shall be included in
;all copies or substantial portions of the Software.
;
;THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
;IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
;FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
;AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
;LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
;OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
;THE SOFTWARE.

; live cell with less than 2 neighbour dies, under-population
; live cell with 2 or 3 neighbour lives
; live cell with more than three neighbours dies, over-crowding
; any dead cell with three live neighbour cells, is now live

(defun check-left  (board i j) (and (<= 0 (- j 1)) (eq 1 (aref board i (- j 1)))))
(defun check-right (board i j cols) (and (> cols (+ j 1)) (eq 1 (aref board i (+ j 1)))))

(defun next-generation (board)
  ; let* = in order, eg, results relys on dims
  (let* ((dims (array-dimensions board)) (results (make-array dims :element-type 'bit)))
    (destructuring-bind (rows cols) dims
      (dotimes (i rows)
        (dotimes (j cols)
          (let ((count 0))
            ; TODO: combine ifs
            (if (and (<= 0 (- i 1)) (eq 1 (aref board (- i 1) j))) (incf count)) ; Up
            (if (and (<= 0 (- i 1)) (<= 0 (- j 1)) (eq 1 (aref board (- i 1) (- j 1)))) (incf count))   ; Up-left
            (if (and (<= 0 (- i 1)) (> cols (+ j 1)) (eq 1 (aref board (- i 1) (+ j 1)))) (incf count)) ; Up-right

            (if (and (< (+ i 1) rows) (eq 1 (aref board (+ i 1) j))) (incf count)) ; Down
            (if (and (< (+ i 1) rows) (<= 0 (- j 1)) (eq 1 (aref board (+ i 1) (- j 1)))) (incf count))   ; Down-left
            (if (and (< (+ i 1) rows) (> cols (+ j 1)) (eq 1 (aref board (+ i 1) (+ j 1)))) (incf count)) ; Donw-right

            (if (check-right board i j cols) (incf count)) ; Right
            (if (check-left board i j) (incf count)) ; Left

            ; update cell
            (if (and (eq (aref board i j) 1) (<= 2 count 3)) (setf (aref results i j) 1))
            (if (and (eq (aref board i j) 0) (eq count 3)) (setf (aref results i j) 1)))))) results))


(defun print-board (board &optional (stream t) )
  (let ((dims (array-dimensions board)))
    (destructuring-bind (rows cols) dims
      (dotimes (i rows board)
        (dotimes (j cols)
          (if (zerop (aref board i j))
            (format stream ". ")
            (format stream "# ")))
          (format stream "~%")))))

(defun ansi-print-board (board &optional (stream t) )
  (let ((dims (array-dimensions board)))
    (destructuring-bind (rows cols) dims
      (dotimes (i rows board)
        (dotimes (j cols)
          (if (zerop (aref board i j))
            (format stream "~a. ~a" ansi-red ansi-clear)
            (format stream "~a# ~a" ansi-green ansi-clear)))
          (format stream "~%")))))

;;;;;;;;;;;;;;;;;;;;
;;;;;; Driver ;;;;;;
;;;;;;;;;;;;;;;;;;;;

; ansi
(setf ansi-clear       (concatenate 'string (list (code-char 27) #\[ #\0 #\m)))
(setf ansi-red         (concatenate 'string (list (code-char 27) #\[ #\3 #\1 #\m)))
(setf ansi-green       (concatenate 'string (list (code-char 27) #\[ #\3 #\2 #\m)))
(setf ansi-yellow      (concatenate 'string (list (code-char 27) #\[ #\3 #\3 #\m)))
(setf ansi-cyan        (concatenate 'string (list (code-char 27) #\[ #\3 #\6 #\m)))
(setf ansi-cursorhome  (concatenate 'string (list (code-char 27) #\[ #\H)))
(setf ansi-clearscreen (concatenate 'string (list (code-char 27) #\[ #\2 #\J)))
; Generation
(setf generation 0)

(setf DEBUG-FILE nil)
;(setf DEBUG-FILE (open "life.txt" :direction :output :if-exists :supersede))

;(let ((sweets-board (make-array '(8 57)
;              :element-type 'bit
;              :initial-contents '(
;(0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0)
;(0 0 0 0 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0)
;(0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0)
;(0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 1 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 1 1 0 0 1 1 0 0 0)
;(0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 1 0 0 1 0 0 1 0 0 1 0 0 1 0 1 1 1 1 1 0 1 1 1 0 0 0 1 0 0 1 0 1 0 0 0 1 0 0 0 0 0)
;(0 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 1 0 1 1 1 1 1 0 1 1 1 1 1 0 0 0 1 0 0 0 1 0 0 1 0 1 1 1 1 1 0 0 1 0 0 0 1 0 0 0 0)
;(0 0 0 0 0 0 0 1 0 1 0 0 1 1 0 0 1 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 1 0 1 0 0 1 0 1 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0)
;(0 0 0 1 1 1 1 0 0 0 1 1 0 0 1 1 0 0 0 1 1 1 1 0 0 1 1 1 1 0 0 0 1 1 0 0 1 0 0 1 0 0 1 1 1 1 0 1 1 0 0 1 1 0 0 0 0)))))
(let ((clisp-board (make-array '(7 42)
       :element-type 'bit
       :initial-contents '(
(0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 0 0 0 1 1 1 1 1 0)
(1 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 1 0 0 1 0 0 0 0 1)
(1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 0 1)
(1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 1 1 1 1 1 0 0 0 1 1 1 1 1 0)
(1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1 0 0 0 0 0)
(1 0 0 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 1 0 0 1 0 0 0 0 0)
(0 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0)))))
;(let ((simple-board (make-array '(5 10)
;                         :element-type 'bit
;                         :initial-contents '(
;                                             (0 0 0 0 0 0 0 0 0 0)
;                                             (0 1 0 0 0 1 0 0 0 0)
;                                             (0 1 0 0 0 1 0 0 0 0)
;                                             (0 1 0 0 0 1 0 0 0 0)
;                                             (0 0 0 0 0 0 0 0 0 0)
;  ))))
  (format t "~a" ansi-clearscreen)
  (let ((board clisp-board))
    (loop
      (incf generation)
      (format t "~a" ansi-cursorhome)
      (format t "~aConway's Game of Life~a~%~%" ansi-green ansi-clear)
      (ansi-print-board board)
      (format t "~%~%~aGeneration: ~d~a" ansi-cyan generation ansi-clear)
      ; DEBUG
      (if DEBUG-FILE
        (progn (format DEBUG-FILE "Generation: ~d:~%" generation)
          (print-board board DEBUG-FILE)
          (format DEBUG-FILE "~%~%~%")))
      ; E/O debug
      (setf board (next-generation board))
      (if (equal generation 1)
        (and (read) (format t "~a" ansi-clearscreen))
        (sleep 0.5)))))
