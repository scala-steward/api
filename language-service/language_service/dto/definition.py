from marshmallow import Schema, fields


class Definition:
    def __init__(self, subdefinitions=None, tag="", examples=None):
        self.subdefinitions = subdefinitions
        self.tag = tag
        self.examples = examples

    def set_subdefinitions(self, subdefinitions):
        self.subdefinitions = subdefinitions

    def __repr__(self):
        return "Definition(subdefinitions: %s, tag: %s, examples: %s)" % (
            self.subdefinitions,
            self.tag,
            self.examples,
        )


class ChineseDefinition(Definition):
    def __init__(
        self,
        subdefinitions=None,
        tag="",
        examples=None,
        pinyin=None,
        simplified=None,
        traditional=None,
        hsk=None,
    ):
        self.subdefinitions = subdefinitions
        self.tag = tag
        self.examples = examples
        self.pinyin = pinyin
        self.simplified = simplified
        self.traditional = traditional
        self.hsk = hsk

    def __repr__(self):
        return (
            "ChineseDefinition(subdefinitions: %s, tag: %s, examples: %s, pinyin: %s, simplified: %s, traditional: %s, hsk: %s)"
            % (
                self.subdefinitions,
                self.tag,
                self.examples,
                self.pinyin,
                self.simplified,
                self.traditional,
                self.hsk,
            )
        )


class DefinitionSchema(Schema):
    subdefinitions = fields.List(fields.Str())
    tag = fields.Str()
    examples = fields.List(fields.Str())
    pinyin = fields.Str()
    simplified = fields.Str()
    traditional = fields.Str()
    hsk = fields.Integer()