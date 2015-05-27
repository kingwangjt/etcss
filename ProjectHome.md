# E.T.CSS. #

## Emotion Tagger for Chinese Sentences Sentiment. ##
The project is an emotion tagger for Chinese sentences sentiment based on Support Vector Machine. The original data should be "Part of speech-Tagged".

For example, a common data input should be like this:
"天津大学/nt 会/v 成为/v 世界/n 一流/b 大学/n 吗/y ？/w ".

The sentence above is "Part of speech-Tagged", each letter(s) after slash indicates the part-of-speech of the given word. Since Chinese words are not separated by space (or anything else), a pre-processing called "Chinese words segment" is needed. A common algorithm may use mathematical models like HMM.

The output will be fine-grained "emotional vectors" of the given sentences. This process is based on support vector machine. The SVM classifier is trained as supervised learning, and the classifier contains extensible multiple emotional categories.
Another Google code project **"optimized-ictclas4j"** is a related HMM-based Chinese words segment implement. (See external links).

## 项目概述 ##
基于支持向量机分类器模型对经过中文分词和词性标注的中文句进行细粒度情感分类，分类器采用有监督学习方式训练。情感分类包含可扩充的多个情感类。

Licensed under **APL v2.0** & **CC BY-NC-SA 3.0**.

在 **APL v2.0** 及 **CC BY-NC-SA 3.0** 协议下发布，请遵照协议规范使用。

任何问题请联系 [mailto://wenqs27@gmail.com](mailto://wenqs27@gmail.com)